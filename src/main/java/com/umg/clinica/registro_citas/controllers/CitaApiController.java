package com.umg.clinica.registro_citas.controllers;

import com.umg.clinica.registro_citas.modelo.Cita;
import com.umg.clinica.registro_citas.repository.CitaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaApiController {

    @Autowired
    private CitaRepository citaRepository;

    @GetMapping
    public List<Cita> listarTodas() {
        return citaRepository.findAll();
    }

    @PostMapping
    public Cita registrar(@Valid @RequestBody Cita cita) {
        // Aseguramos que el estado sea "Programada" si viene nulo, 
        // tal como definimos en el SQL
        if (cita.getEstado() == null) {
            cita.setEstado("Programada");
        }
        return citaRepository.save(cita);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerPorId(@PathVariable Integer id) {
        return citaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizar(@PathVariable Integer id, @Valid @RequestBody Cita detalles) {
        return citaRepository.findById(id)
                .map(cita -> {
                    cita.setPaciente(detalles.getPaciente());
                    cita.setMedico(detalles.getMedico());
                    cita.setFecha(detalles.getFecha());
                    cita.setHora(detalles.getHora());
                    cita.setMotivo(detalles.getMotivo());
                    cita.setEstado(detalles.getEstado());
                    return ResponseEntity.ok(citaRepository.save(cita));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Cita> actualizarEstado(@PathVariable Integer id, @RequestBody java.util.Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        
        return citaRepository.findById(id)
                .map(cita -> {
                    cita.setEstado(nuevoEstado);
                    return ResponseEntity.ok(citaRepository.save(cita));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (citaRepository.existsById(id)) {
            citaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}