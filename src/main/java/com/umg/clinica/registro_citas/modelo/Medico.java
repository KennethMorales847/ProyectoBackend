package com.umg.clinica.registro_citas.modelo;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "medicos", schema = "public")
@Data
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Integer id;

    @Column(name = "nombre_medico", nullable = false)
    private String nombre;

    @Column(name = "fecha_nacimiento", nullable = false)
    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd") // <-- USA ESTA ANOTACIÓN
    private LocalDate fechaNacimiento;

    @Column(name = "direccion_medico", nullable = false)
    private String direccion;

    @Column(name = "tel_medico", nullable = false)
    private String telefono;

    @Column(name = "especialidad", nullable = false)
    private String especialidad;

    @Column(name = "fecha_colegiacion", nullable = false)
    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd") // <-- USA ESTA ANOTACIÓN
    private LocalDate fechaColegiacion;

    @Column(name = "num_colegiado", nullable = false, unique = true)
    private String numColegiado;
}