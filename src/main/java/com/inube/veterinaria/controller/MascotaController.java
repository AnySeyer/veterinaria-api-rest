
package com.inube.veterinaria.controller;

import com.inube.veterinaria.dto.ApiResponse;
import com.inube.veterinaria.dto.MascotaRequest;
import com.inube.veterinaria.model.MascotaModel;
import com.inube.veterinaria.model.VacunaModel;
import com.inube.veterinaria.service.MascotaService;
import com.inube.veterinaria.service.VacunaService;
import com.inube.veterinaria.util.UtilConstants;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService service;
    private final VacunaService vacunaService;

    // Registrar mascota
    @PostMapping
    public ResponseEntity<ApiResponse<MascotaModel>> guardar(
            @RequestBody MascotaRequest request) {

        MascotaModel resultado = service.guardar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        UtilConstants.MSG7,
                        resultado
                ));
    }

    // Listar mascotas activas
    @GetMapping
    public ResponseEntity<ApiResponse<List<MascotaModel>>> listar() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG1,
                        service.listar()
                )
        );
    }

    // Buscar mascota por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MascotaModel>> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG1,
                        service.buscarPorId(id)
                )
        );
    }

    // Historial de vacunas de una mascota
    @GetMapping("/{id}/vacunas")
    public ResponseEntity<ApiResponse<List<VacunaModel>>> listarVacunas(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG1,
                        vacunaService.buscarPorMascota(id)
                )
        );
    }

    // Asignar un adoptante a una mascota existente
    @PutMapping("/{idMascota}/adoptante/{idAdoptante}")
    public ResponseEntity<ApiResponse<MascotaModel>> asignarAdoptante(
            @PathVariable Long idMascota,
            @PathVariable Long idAdoptante) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG21,
                        service.asignarAdoptante(idMascota, idAdoptante)
                )
        );
    }
}
