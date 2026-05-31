package com.umg.clinica.registro_citas.Servicio;

import com.umg.clinica.registro_citas.modelo.Usuario;
import com.umg.clinica.registro_citas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;

// import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new User(
            usuario.getUsername(), 
            usuario.getPassword(), 
            // ESTO es lo que cambia: le pasamos el rol de la DB
            Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol())) 
        );
    }
}