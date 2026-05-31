package com.umg.clinica.registro_citas.Servicio;

import com.umg.clinica.registro_citas.modelo.Medico;
import com.umg.clinica.registro_citas.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public List<Medico> obtenerTodos() {
        return medicoRepository.findAll();
    }

    public Medico guardarMedico(Medico medico) {
        Medico existente = medicoRepository.findByNumColegiado(medico.getNumColegiado());
        if (existente != null) {
            throw new RuntimeException("Error: El número de colegiado ya existe.");
        }
        return medicoRepository.save(medico);
    }
}