package com.inube.veterinaria.dto;

public record MascotaRequest(
        String nombre,
        String especie,
        String raza,
        Integer edad,
        String sexo,
        Long idAdoptante
) {
}