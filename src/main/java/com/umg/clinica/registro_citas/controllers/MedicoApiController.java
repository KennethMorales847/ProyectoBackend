package com.umg.clinica.registro_citas.controllers;

import com.umg.clinica.registro_citas.modelo.Medico;
import com.umg.clinica.registro_citas.repository.MedicoRepository;
import com.umg.clinica.registro_citas.Servicio.MedicoService; // Importamos tu servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoApiController {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private MedicoService medicoService; // <-- Inyectamos el servicio aquí

    @GetMapping
    public List<Medico> obtenerTodos() {
        // Usamos el servicio en lugar del repositorio directamente
        return medicoService.obtenerTodos(); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> obtenerPorId(@PathVariable Integer id) {
        return medicoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    @PostMapping
    public ResponseEntity<?> crearMedico(@RequestBody Medico medico) {
        try {
            // Usamos tu servicio que tiene la lógica de validación
            Medico nuevoMedico = medicoService.guardarMedico(medico);
            return ResponseEntity.ok(nuevoMedico);
        } catch (RuntimeException e) {
            // Si el colegiado está repetido, atrapa tu mensaje de error y lo manda a Angular
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> actualizarMedico(@PathVariable Integer id, @RequestBody Medico detalles) {
        return medicoRepository.findById(id)
                .map(medico -> {
                    medico.setNombre(detalles.getNombre());
                    medico.setFechaNacimiento(detalles.getFechaNacimiento());
                    medico.setDireccion(detalles.getDireccion());
                    medico.setTelefono(detalles.getTelefono());
                    medico.setEspecialidad(detalles.getEspecialidad());
                    medico.setFechaColegiacion(detalles.getFechaColegiacion());
                    medico.setNumColegiado(detalles.getNumColegiado());
                    
                    return ResponseEntity.ok(medicoRepository.save(medico));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedico(@PathVariable Integer id) {
        if (medicoRepository.existsById(id)) {
            medicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}