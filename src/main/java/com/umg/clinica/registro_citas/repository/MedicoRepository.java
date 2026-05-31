package com.umg.clinica.registro_citas.repository;

import com.umg.clinica.registro_citas.modelo.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {
    Medico findByNumColegiado(String numColegiado);
}