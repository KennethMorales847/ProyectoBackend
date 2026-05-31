package com.umg.clinica.registro_citas.Servicio;

import com.umg.clinica.registro_citas.modelo.Paciente;
import com.umg.clinica.registro_citas.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    // 1. Obtener todos los pacientes
    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findAll();
    }

    // 2. Obtener un paciente por ID
    public Optional<Paciente> obtenerPorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    // 3. Crear un nuevo paciente
    public Paciente crearPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    // 4. Actualizar un paciente
    public Optional<Paciente> actualizarPaciente(Integer id, Paciente detalles) {
        return pacienteRepository.findById(id).map(paciente -> {
            paciente.setNombre(detalles.getNombre());
            paciente.setFechaNacimiento(detalles.getFechaNacimiento());
            paciente.setDireccion(detalles.getDireccion());
            paciente.setTelefono(detalles.getTelefono());
            paciente.setSeguro(detalles.getSeguro());
            return pacienteRepository.save(paciente);
        });
    }

    // 5. Eliminar un paciente
    public boolean eliminarPaciente(Integer id) {
        if (pacienteRepository.existsById(id)) {
            pacienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}