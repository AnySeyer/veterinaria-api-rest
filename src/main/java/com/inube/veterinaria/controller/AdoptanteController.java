
package com.inube.veterinaria.controller;

import com.inube.veterinaria.dto.ApiResponse;
import com.inube.veterinaria.model.AdoptanteModel;
import com.inube.veterinaria.model.MascotaModel;
import com.inube.veterinaria.service.AdoptanteService;
import com.inube.veterinaria.service.MascotaService;
import com.inube.veterinaria.util.UtilConstants;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adoptantes")
@RequiredArgsConstructor
public class AdoptanteController {

    private final AdoptanteService service;
    private final MascotaService mascotaService;

    // Registrar adoptante
    @PostMapping
    public ResponseEntity<ApiResponse<AdoptanteModel>> guardar(
            @RequestBody AdoptanteModel adoptante) {

        AdoptanteModel resultado = service.guardar(adoptante);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        UtilConstants.MSG2,
                        resultado
                ));
    }

    // Listar adoptantes activos
    @GetMapping
    public ResponseEntity<ApiResponse<List<AdoptanteModel>>> listar() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG1,
                        service.listar()
                )
        );
    }

    // Buscar adoptante por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AdoptanteModel>> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG1,
                        service.buscarPorId(id)
                )
        );
    }

    // Actualizar adoptante
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AdoptanteModel>> actualizar(
            @PathVariable Long id,
            @RequestBody AdoptanteModel request) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG3,
                        service.actualizar(id, request)
                )
        );
    }

    // Eliminación lógica del adoptante
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG4,
                        null
                )
        );
    }

    // Consultar mascotas de un adoptante
    @GetMapping("/{id}/mascotas")
    public ResponseEntity<ApiResponse<List<MascotaModel>>> listarMascotas(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG1,
                        mascotaService.buscarPorAdoptante(id)
                )
        );
    }
}
