package com.umg.clinica.registro_citas.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.umg.clinica.registro_citas.Servicio.UserDetailsServiceImpl;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/auth/") || "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Extraer el token del encabezado
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extraerUsername(jwt);
            } catch (Exception e) {
                logger.error("No se pudo extraer el username del token");
            }
        }

        // Validar el token y cargar el usuario desde la DB
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // === AQUÍ ESTÁ LA LÍNEA QUE RECONSTRUYE USERDETAILS ===
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Validamos el token contra el usuario cargado
            if (jwtUtil.validarToken(jwt, userDetails.getUsername())) {
                
                // 1. Extraemos el rol del token usando el método de tu JwtUtil
                String rolDelToken = jwtUtil.extraerRol(jwt); 
                
                // 2. Creamos la autoridad que Spring Security entiende (ej. ROLE_ADMIN)
                java.util.List<org.springframework.security.core.GrantedAuthority> authorities = 
                    org.springframework.security.core.authority.AuthorityUtils.createAuthorityList(rolDelToken);

                // 3. Le pasamos las authorities creadas del token a la autenticación
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Establecer la autenticación en el contexto de Spring
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}