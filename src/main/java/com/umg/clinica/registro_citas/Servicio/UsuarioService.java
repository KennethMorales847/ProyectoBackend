package com.umg.clinica.registro_citas.Servicio;

import com.umg.clinica.registro_citas.modelo.Usuario;
import com.umg.clinica.registro_citas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public Optional<Usuario> actualizarUsuario(Long id, Usuario datos) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setUsername(datos.getUsername());
            usuario.setRol(datos.getRol());

            if (datos.getPassword() != null && !datos.getPassword().isBlank()) {
                usuario.setPassword(passwordEncoder.encode(datos.getPassword()));
            }

            return usuarioRepository.save(usuario);
        });
    }

    public boolean eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            return false;
        }

        usuarioRepository.deleteById(id);
        return true;
    }
}