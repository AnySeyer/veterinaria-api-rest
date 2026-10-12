
package com.inube.veterinaria.dto;

import java.time.LocalDateTime;

public record VacunaRequest(
        String nombreVacuna,
        String descripcion,
        LocalDateTime proximaAplicacion,
        Long idMascota
) {
}
