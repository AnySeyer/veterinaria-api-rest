package com.inube.veterinaria.dto;

import java.time.LocalDateTime;

public record CitaSeguimientoRequest(
        Long idMascota,
        LocalDateTime fechaCita,
        String motivo,
        String observaciones
) {
}
