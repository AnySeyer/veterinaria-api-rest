package com.inube.veterinaria.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "vacunas")
@Data
public class VacunaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vacunas")
    @SequenceGenerator(
            name = "seq_vacunas",
            sequenceName = "seq_vacunas",
            allocationSize = 1
    )
    @Column(name = "id_vacuna")
    private Long idVacuna;

    @Column(name = "nombre_vacuna", nullable = false)
    private String nombreVacuna;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_aplicacion", nullable = false)
    private LocalDateTime fechaAplicacion;

    @Column(name = "proxima_aplicacion")
    private LocalDateTime proximaAplicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mascota", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "adoptante"})
    private MascotaModel mascota;

    @Column(name = "estado")
    private Integer estado = 1;
}