package com.inube.veterinaria.controller;
import com.inube.veterinaria.dto.ApiResponse;
import com.inube.veterinaria.dto.VacunaRequest;
import com.inube.veterinaria.model.VacunaModel;
import com.inube.veterinaria.service.VacunaService;
import com.inube.veterinaria.util.UtilConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacunas")
@RequiredArgsConstructor
public class VacunaController {

    private final VacunaService service;

    // Registrar vacuna
    @PostMapping
    public ResponseEntity<ApiResponse<VacunaModel>> guardar(
            @RequestBody VacunaRequest request) {

        VacunaModel resultado = service.guardar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        UtilConstants.MSG11,
                        resultado
                ));
    }
    // Listar vacunas activas
    @GetMapping
    public ResponseEntity<ApiResponse<List<VacunaModel>>> listar() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG1,
                        service.listar()
                )
        );
    }
}
