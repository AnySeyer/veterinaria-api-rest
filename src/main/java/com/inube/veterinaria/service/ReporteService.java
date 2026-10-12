
package com.inube.veterinaria.service;

import com.inube.veterinaria.dto.ReporteMascotasVacunadasDTO;
import com.inube.veterinaria.repository.VacunaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {

    private final VacunaRepository vacunaRepository;

    public ReporteService(VacunaRepository vacunaRepository) {
        this.vacunaRepository = vacunaRepository;
    }

    public List<ReporteMascotasVacunadasDTO> obtenerMascotasVacunadas() {

        List<Object[]> resultados =
                vacunaRepository.reporteMascotasVacunadas();

        return resultados.stream()
                .map(fila -> new ReporteMascotasVacunadasDTO(
                        (String) fila[0],
                        ((Number) fila[1]).longValue(),
                        (String) fila[2],
                        ((Number) fila[3]).longValue()
                ))
                .toList();
    }
}
