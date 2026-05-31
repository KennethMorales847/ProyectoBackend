package com.umg.clinica.registro_citas.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "seguro", schema = "public")
@Data
public class Seguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguro")
    private Integer id;

    @Column(name = "nombre_seguro", nullable = false)
    private String nombre;

    @Column(name = "poliza_seguro", nullable = false)
    private String poliza;
}