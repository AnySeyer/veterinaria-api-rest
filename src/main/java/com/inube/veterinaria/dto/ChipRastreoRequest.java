
package com.inube.veterinaria.dto;

public record ChipRastreoRequest(
        String codigoChip,
        String fabricante,
        Long idMascota
) {
}
