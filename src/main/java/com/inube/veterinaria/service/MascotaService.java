package com.inube.veterinaria.service;
import com.inube.veterinaria.dto.MascotaRequest;
import com.inube.veterinaria.model.AdoptanteModel;
import com.inube.veterinaria.model.MascotaModel;
import com.inube.veterinaria.repository.AdoptanteRepository;
import com.inube.veterinaria.repository.MascotaRepository;
import com.inube.veterinaria.util.UtilConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MascotaService {

    private final MascotaRepository repository;
    private final AdoptanteRepository adoptanteRepository;

    public MascotaModel guardar(MascotaRequest request) {

        if (request.nombre() == null || request.nombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la mascota es obligatorio"
            );
        }

        if (request.especie() == null || request.especie().isBlank()) {
            throw new IllegalArgumentException(
                    "La especie es obligatoria"
            );
        }

        if (request.edad() != null && request.edad() < 0) {
            throw new IllegalArgumentException(
                    "La edad no puede ser negativa"
            );
        }

        MascotaModel mascota = new MascotaModel();

        mascota.setNombre(request.nombre());
        mascota.setEspecie(request.especie());
        mascota.setRaza(request.raza());
        mascota.setEdad(request.edad());
        mascota.setSexo(request.sexo());
        mascota.setEstado(UtilConstants.CODEPOS);
        mascota.setFechaIngreso(LocalDateTime.now());

        if (request.idAdoptante() != null) {

            AdoptanteModel adoptante = adoptanteRepository
                    .findById(request.idAdoptante())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    UtilConstants.MSG5
                            )
                    );

            if (adoptante.getEstado() == null ||
                    adoptante.getEstado() != UtilConstants.CODEPOS) {
                throw new IllegalArgumentException(
                        UtilConstants.MSG6
                );
            }

            mascota.setAdoptante(adoptante);
            mascota.setFechaAdopcion(LocalDateTime.now());
        }

        return repository.save(mascota);
    }

    public List<MascotaModel> listar() {
        return repository.findByEstado(UtilConstants.CODEPOS);
    }

    public MascotaModel buscarPorId(Long id) {

        MascotaModel mascota = repository.findById(id)
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

        return mascota;
    }

    public List<MascotaModel> buscarPorAdoptante(Long idAdoptante) {

        AdoptanteModel adoptante = adoptanteRepository.findById(idAdoptante)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                UtilConstants.MSG5
                        )
                );

        if (adoptante.getEstado() == null ||
                adoptante.getEstado() != UtilConstants.CODEPOS) {
            throw new IllegalArgumentException(
                    UtilConstants.MSG6
            );
        }

        return repository.findByAdoptante_IdAdoptanteAndEstado(
                idAdoptante,
                UtilConstants.CODEPOS
        );
    }

    // Asignar un adoptante a una mascota existente
    public MascotaModel asignarAdoptante(
            Long idMascota,
            Long idAdoptante
    ) {

        MascotaModel mascota = buscarPorId(idMascota);

        AdoptanteModel adoptante = adoptanteRepository
                .findById(idAdoptante)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                UtilConstants.MSG5
                        )
                );

        if (adoptante.getEstado() == null ||
                adoptante.getEstado() != UtilConstants.CODEPOS) {
            throw new IllegalArgumentException(
                    UtilConstants.MSG6
            );
        }

        if (mascota.getAdoptante() != null) {
            throw new IllegalArgumentException(
                    UtilConstants.MSG10
            );
        }

        mascota.setAdoptante(adoptante);
        mascota.setFechaAdopcion(LocalDateTime.now());

        return repository.save(mascota);
    }
}
