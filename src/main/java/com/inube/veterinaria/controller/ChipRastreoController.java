
package com.inube.veterinaria.controller;

import com.inube.veterinaria.dto.ApiResponse;
import com.inube.veterinaria.dto.ChipRastreoRequest;
import com.inube.veterinaria.model.ChipRastreoModel;
import com.inube.veterinaria.service.ChipRastreoService;
import com.inube.veterinaria.util.UtilConstants;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chips")
@RequiredArgsConstructor
public class ChipRastreoController {

    private final ChipRastreoService service;

    // Registrar chip de rastreo
    @PostMapping
    public ResponseEntity<ApiResponse<ChipRastreoModel>> guardar(
            @RequestBody ChipRastreoRequest request) {

        ChipRastreoModel resultado = service.guardar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        UtilConstants.MSG14,
                        resultado
                ));
    }

    // Listar chips activos
    @GetMapping
    public ResponseEntity<ApiResponse<List<ChipRastreoModel>>> listar() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG1,
                        service.listar()
                )
        );
    }

    // Consultar chips de una mascota
    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<ApiResponse<List<ChipRastreoModel>>> buscarPorMascota(
            @PathVariable Long idMascota) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG1,
                        service.buscarPorMascota(idMascota)
                )
        );
    }
}
