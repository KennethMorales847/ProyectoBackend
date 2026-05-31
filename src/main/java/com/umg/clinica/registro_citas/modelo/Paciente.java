package com.umg.clinica.registro_citas.modelo;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "pacientes", schema = "public")
@Data
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_seguro", nullable = false)
    private Seguro seguro;

    @Column(name = "nombre_paciente", nullable = false)
    private String nombre;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "direccion_paciente", nullable = false)
    private String direccion;

    @Column(name = "tel_paciente", nullable = false)
    private String telefono;
}