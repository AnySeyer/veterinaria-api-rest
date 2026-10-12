
package com.inube.veterinaria.service;

import com.inube.veterinaria.dto.VacunaRequest;
import com.inube.veterinaria.model.MascotaModel;
import com.inube.veterinaria.model.VacunaModel;
import com.inube.veterinaria.repository.MascotaRepository;
import com.inube.veterinaria.repository.VacunaRepository;
import com.inube.veterinaria.util.UtilConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacunaService {

    private final VacunaRepository repository;
    private final MascotaRepository mascotaRepository;

    public VacunaModel guardar(VacunaRequest request) {

        // Validar nombre de la vacuna
        if (request.nombreVacuna() == null ||
                request.nombreVacuna().isBlank()) {
            throw new IllegalArgumentException(
                    UtilConstants.MSG12
            );
        }

        // Validar ID de mascota
        if (request.idMascota() == null) {
            throw new IllegalArgumentException(
                    "Debes indicar el ID de la mascota"
            );
        }

        // Buscar mascota
        MascotaModel mascota = mascotaRepository
                .findById(request.idMascota())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                UtilConstants.MSG8
                        )
                );

        // Validar que la mascota esté activa
        if (mascota.getEstado() == null ||
                mascota.getEstado() != UtilConstants.CODEPOS) {
            throw new IllegalArgumentException(
                    UtilConstants.MSG9
            );
        }

        // Crear registro de vacuna
        VacunaModel vacuna = new VacunaModel();

        vacuna.setNombreVacuna(request.nombreVacuna());
        vacuna.setDescripcion(request.descripcion());

        // Registrar fecha de aplicación automáticamente
        LocalDateTime fechaAplicacion = LocalDateTime.now();
        vacuna.setFechaAplicacion(fechaAplicacion);

        // Calcular próxima aplicación automáticamente
        // Regla de prueba: 12 meses después
        vacuna.setProximaAplicacion(
                fechaAplicacion.plusMonths(12)
        );

        // Asociar mascota y activar vacuna
        vacuna.setMascota(mascota);
        vacuna.setEstado(UtilConstants.CODEPOS);

        // Guardar vacuna en Oracle
        return repository.save(vacuna);
    }

    public List<VacunaModel> listar() {
        return repository.findByEstado(
                UtilConstants.CODEPOS
        );
    }

    public List<VacunaModel> buscarPorMascota(Long idMascota) {

        MascotaModel mascota = mascotaRepository
                .findById(idMascota)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                UtilConstants.MSG8
                        )
                );

        if (mascota.getEstado() == null ||
                mascota.getEstado() != UtilConstants.CODEPOS) {
            throw new IllegalArgumentException(
                    UtilConstants.MSG9
            );
        }

        return repository.findByMascota_IdMascotaAndEstado(
                idMascota,
                UtilConstants.CODEPOS
        );
    }
}
