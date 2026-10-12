
package com.inube.veterinaria.controller;

import com.inube.veterinaria.dto.ApiResponse;
import com.inube.veterinaria.dto.ReporteMascotasVacunadasDTO;
import com.inube.veterinaria.service.ReporteService;
import com.inube.veterinaria.util.UtilConstants;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/mascotas-vacunadas")
    public ResponseEntity<ApiResponse<List<ReporteMascotasVacunadasDTO>>>
    obtenerMascotasVacunadas() {

        List<ReporteMascotasVacunadasDTO> reporte =
                reporteService.obtenerMascotasVacunadas();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        UtilConstants.MSG1,
                        reporte
                )
        );
    }
}
