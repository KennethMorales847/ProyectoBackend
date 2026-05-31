package com.umg.clinica.registro_citas.controllers;

import com.umg.clinica.registro_citas.modelo.Usuario;
import com.umg.clinica.registro_citas.Servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200") // Permiso para Angular
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
}