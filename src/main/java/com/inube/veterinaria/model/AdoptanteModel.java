package com.inube.veterinaria.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "adoptantes")
@Data
public class AdoptanteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_adoptantes")
    @SequenceGenerator(
            name = "seq_adoptantes",
            sequenceName = "seq_adoptantes",
            allocationSize = 1
    )
    @Column(name = "id_adoptante")
    private Long idAdoptante;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "correo")
    private String correo;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "estado")
    private Integer estado = 1;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();
}