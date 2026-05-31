package com.umg.clinica.registro_citas.modelo;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "registrocitas", schema = "public")
@Data
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rcita")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @Column(name = "fecha_cita", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_cita", nullable = false)
    private LocalTime hora;

    @Column(name = "motivo_cita", nullable = false)
    private String motivo;

    @Column(name = "estatus_cita", nullable = false)
    private String estado = "Programada";

    @Column(name = "creado_at", insertable = false, updatable = false)
    private LocalDateTime creadoAt;
}