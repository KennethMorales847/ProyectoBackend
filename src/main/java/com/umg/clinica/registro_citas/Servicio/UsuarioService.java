package com.umg.clinica.registro_citas.Servicio;

import com.umg.clinica.registro_citas.modelo.Usuario;
import com.umg.clinica.registro_citas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(Usuario usuario) {
    // Si el rol viene vacío o nulo, le asignamos ROLE_MEDICO por seguridad
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
        usuario.setRol("ROLE_MEDICO");
        }
    
        // Encriptar contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }
}