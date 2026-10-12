
package com.inube.veterinaria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "chip_rastreo")
@Data
public class ChipRastreoModel {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_chip_rastreo"
    )
    @SequenceGenerator(
            name = "seq_chip_rastreo",
            sequenceName = "seq_chip_rastreo",
            allocationSize = 1
    )
    @Column(name = "id_chip")
    private Long idChip;

    @Column(name = "codigo_chip", nullable = false)
    private String codigoChip;

    @Column(name = "fecha_implantacion")
    private LocalDateTime fechaImplantacion;

    @Column(name = "fabricante")
    private String fabricante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mascota", nullable = false)
    private MascotaModel mascota;

    @Column(name = "estado", nullable = false)
    private Integer estado = 1;
}
