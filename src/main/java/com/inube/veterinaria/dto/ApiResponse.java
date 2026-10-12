package com.inube.veterinaria.dto;

public record ApiResponse<T>(
        String mensaje,
        T datos
) {
}
