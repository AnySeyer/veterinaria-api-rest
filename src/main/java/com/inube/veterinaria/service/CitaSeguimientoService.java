
package com.inube.veterinaria.service;

import com.inube.veterinaria.dto.CitaSeguimientoRequest;
import com.inube.veterinaria.model.AdoptanteModel;
import com.inube.veterinaria.model.CitaSeguimientoModel;
import com.inube.veterinaria.model.MascotaModel;
import com.inube.veterinaria.repository.CitaSeguimientoRepository;
import com.inube.veterinaria.repository.MascotaRepository;
import com.inube.veterinaria.repository.VacunaRepository;
import com.inube.veterinaria.util.UtilConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaSeguimientoService {

    private final CitaSeguimientoRepository repository;
    private final MascotaRepository mascotaRepository;
    private final VacunaRepository vacunaRepository;

    public CitaSeguimientoModel guardar(CitaSeguimientoRequest request) {

        if (request.idMascota() == null) {
            throw new IllegalArgumentException(
                    "Debes indicar el ID de la mascota"
            );
        }

        MascotaModel mascota = mascotaRepository
                .findById(request.idMascota())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                UtilConstants.MSG8
                        )
                );

        if (mascota.getEstado() == null ||
                mascota.getEstado() != UtilConstants.CODEPOS) {
            throw new IllegalArgumentException(
                    "No se puede programar una cita para una mascota inactiva"
            );
        }

        // Validar que la mascota tenga un adoptante
        AdoptanteModel adoptante = mascota.getAdoptante();

        if (adoptante == null) {
            throw new IllegalArgumentException(
                    "La mascota debe tener un adoptante asignado"
            );
        }

        // Validar que el adoptante esté activo
        if (adoptante.getEstado() == null ||
                adoptante.getEstado() != UtilConstants.CODEPOS) {
            throw new IllegalArgumentException(
                    "El adoptante de la mascota está inactivo"
            );
        }

        // Validar que tenga al menos una vacuna activa
        if (vacunaRepository.findByMascota_IdMascotaAndEstado(
                request.idMascota(),
                UtilConstants.CODEPOS
        ).isEmpty()) {
            throw new IllegalArgumentException(
                    UtilConstants.MSG13
            );
        }

        // Validar la fecha de la cita
        if (request.fechaCita() == null) {
            throw new IllegalArgumentException(
                    "La fecha de la cita es obligatoria"
            );
        }

        if (!request.fechaCita().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "La fecha de la cita debe ser futura"
            );
        }

        // Validar el motivo
        if (request.motivo() == null || request.motivo().isBlank()) {
            throw new IllegalArgumentException(
                    "El motivo de la cita es obligatorio"
            );
        }

        CitaSeguimientoModel cita = new CitaSeguimientoModel();

        cita.setMascota(mascota);
        cita.setFechaCita(request.fechaCita());
        cita.setMotivo(request.motivo());
        cita.setObservaciones(request.observaciones());

        // Estatus inicial y estado activo
        cita.setEstatus(UtilConstants.CODE1);
        cita.setEstado(UtilConstants.CODEPOS);

        return repository.save(cita);
    }

    public List<CitaSeguimientoModel> listar() {
        return repository.findByEstado(UtilConstants.CODEPOS);
    }

    public List<CitaSeguimientoModel> buscarPorMascota(Long idMascota) {

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

    public void cancelar(Long idCita) {

        CitaSeguimientoModel cita = repository
                .findById(idCita)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                UtilConstants.MSG19
                        )
                );

        if (cita.getEstado() == null ||
                cita.getEstado() != UtilConstants.CODEPOS) {
            throw new IllegalArgumentException(
                    UtilConstants.MSG20
            );
        }

        // Cancelación lógica: no se elimina de Oracle
        cita.setEstado(UtilConstants.CODENEG);
        cita.setEstatus(UtilConstants.CODE2);

        repository.save(cita);
    }
}
