
package com.inube.veterinaria.dto;

public record ReporteMascotasVacunadasDTO(
        String especie,
        Long idMascota,
        String nombreMascota,
        Long cantidadVacunas
) {
}
