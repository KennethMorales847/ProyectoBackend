package com.umg.clinica.registro_citas.controllers;

import com.umg.clinica.registro_citas.modelo.Usuario;
import com.umg.clinica.registro_citas.Servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // ESTE ES EL MÉTODO QUE ANGULAR ESTÁ BUSCANDO PARA LLENAR LA TABLA
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // Solo el administrador puede ver la lista
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        try {
            // Asegúrate de que el nombre del método coincida con el que tienes en UsuarioService
            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios(); 
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario datos) {
        Optional<Usuario> usuarioActualizado = usuarioService.actualizarUsuario(id, datos);
        return usuarioActualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (usuarioService.eliminarUsuario(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}