package com.umg.clinica.registro_citas.repository;

import com.umg.clinica.registro_citas.modelo.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguroRepository extends JpaRepository<Seguro, Integer> {
}
