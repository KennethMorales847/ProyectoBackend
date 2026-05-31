package com.umg.clinica.registro_citas.Servicio;

import com.umg.clinica.registro_citas.modelo.Cita;
import com.umg.clinica.registro_citas.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    // 1. Obtener todas las citas (Usado por listarTodas() en el controlador)
    public List<Cita> obtenerTodas() {
        return citaRepository.findAll();
    }

    // 2. Buscar una cita específica (Usado por obtenerPorId())
    public Optional<Cita> obtenerPorId(Integer idCita) {
        return citaRepository.findById(idCita);
    }

    // 3. Programar / Guardar una cita (Usado por registrar())
    public Cita registrarCita(Cita cita) {
        // Tu regla de negocio: Si no trae estado, por defecto es 'Programada'
        if (cita.getEstado() == null || cita.getEstado().isEmpty()) {
            cita.setEstado("Programada");
        }
        
        // Nota: Si la fecha y hora chocan para el mismo médico, 
        // PostgreSQL lanzará una excepción gracias al constraint UNIQUE.
        return citaRepository.save(cita);
    }

    // 4. Actualización completa de la cita (Usado por actualizar())
    public Optional<Cita> actualizarCita(Integer id, Cita detalles) {
        return citaRepository.findById(id).map(cita -> {
            cita.setPaciente(detalles.getPaciente());
            cita.setMedico(detalles.getMedico());
            cita.setFecha(detalles.getFecha());
            cita.setHora(detalles.getHora());
            cita.setMotivo(detalles.getMotivo());
            cita.setEstado(detalles.getEstado());
            return citaRepository.save(cita);
        });
    }

    // 5. Cambiar estado dinámicamente desde el selector de Angular (Usado por actualizarEstado())
    public Optional<Cita> cambiarEstado(Integer id, String nuevoEstado) {
        return citaRepository.findById(id).map(cita -> {
            cita.setEstado(nuevoEstado); // Soporta 'En Espera', 'Atendido', 'Cancelada', etc.
            return citaRepository.save(cita);
        });
    }

    // 6. Eliminar cita de la base de datos (Usado por eliminar())
    public boolean eliminarCita(Integer id) {
        if (citaRepository.existsById(id)) {
            citaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 7. Tu método original: Obtener el historial de un paciente
    // (Útil para cuando agregues pantallas de historial en Angular)
    public List<Cita> obtenerPorPaciente(Integer idPaciente) {
        return citaRepository.findByPacienteId(idPaciente);
    }
}