package com.umg.clinica.registro_citas.repository;

import com.umg.clinica.registro_citas.modelo.Cita;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> findByPacienteId(Integer idPaciente);
}