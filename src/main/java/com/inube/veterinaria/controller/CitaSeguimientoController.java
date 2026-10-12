
package com.inube.veterinaria.controller;

import com.inube.veterinaria.dto.ApiResponse;
import com.inube.veterinaria.dto.CitaSeguimientoRequest;
import com.inube.veterinaria.model.CitaSeguimientoModel;
import com.inube.veterinaria.service.CitaSeguimientoService;
import com.inube.veterinaria.util.UtilConstants;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaSeguimientoController {

    private final CitaSeguimientoService service;

    // Registrar cita de seguimiento
    @PostMapping
    public ResponseEntity<ApiResponse<CitaSeguimientoModel>> guardar(
            @RequestBody CitaSeguimientoRequest request) {

        CitaSeguimientoModel resultado = service.guardar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        UtilConstants.MSG17,
                        resultado
                ));
    }

    // Listar citas activas
    @GetMapping
    public ResponseEntity<ApiResponse<List<CitaSeguimientoModel>>> listar() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG1,
                        service.listar()
                )
        );
    }

    // Consultar citas activas de una mascota
    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<ApiResponse<List<CitaSeguimientoModel>>> buscarPorMascota(
            @PathVariable Long idMascota) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG1,
                        service.buscarPorMascota(idMascota)
                )
        );
    }

    // Cancelación lógica de una cita
    @DeleteMapping("/{idCita}")
    public ResponseEntity<ApiResponse<Void>> cancelar(
            @PathVariable Long idCita) {

        service.cancelar(idCita);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG18,
                        null
                )
        );
    }
}
