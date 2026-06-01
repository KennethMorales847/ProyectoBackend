package com.umg.clinica.registro_citas.controllers;

import com.umg.clinica.registro_citas.modelo.Paciente;
import com.umg.clinica.registro_citas.Servicio.PacienteService; // 💡 IMPORTANTE: Importamos tu clase de Servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteApiController {

    @Autowired
    private PacienteService pacienteService; // 💡 CAMBIO: Inyectamos la clase directa de Servicio en lugar del repositorio

    @GetMapping
    public List<Paciente> obtenerTodos() {
        return pacienteService.obtenerTodos(); // 💡 Cambiado a tu servicio
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPorId(@PathVariable Integer id) {
        return pacienteService.obtenerPorId(id) // 💡 Cambiado a tu servicio
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Paciente crearPaciente(@RequestBody Paciente paciente) {
        return pacienteService.crearPaciente(paciente); // 💡 Cambiado a tu servicio
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizarPaciente(@PathVariable Integer id, @RequestBody Paciente detalles) {
        // 💡 CAMBIO: Delegamos la actualización completa al servicio para mantener limpio el controlador
        return pacienteService.actualizarPaciente(id, detalles)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Integer id) {
        // 💡 CAMBIO: El servicio ahora valida si existe y nos devuelve un booleano (true/false)
        if (pacienteService.eliminarPaciente(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}