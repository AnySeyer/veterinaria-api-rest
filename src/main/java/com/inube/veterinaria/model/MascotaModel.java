package com.inube.veterinaria.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "mascotas")
@Data
public class MascotaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mascotas")
    @SequenceGenerator(
            name = "seq_mascotas",
            sequenceName = "seq_mascotas",
            allocationSize = 1
    )
    @Column(name = "id_mascota")
    private Long idMascota;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "especie", nullable = false)
    private String especie;

    @Column(name = "raza")
    private String raza;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "fecha_ingreso")
    private LocalDateTime fechaIngreso = LocalDateTime.now();

    @Column(name = "fecha_adopcion")
    private LocalDateTime fechaAdopcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_adoptante")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AdoptanteModel adoptante;

    @Column(name = "estado")
    private Integer estado = 1;
}