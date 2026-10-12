package com.inube.veterinaria.service;
import com.inube.veterinaria.dto.ChipRastreoRequest;
import com.inube.veterinaria.model.ChipRastreoModel;
import com.inube.veterinaria.model.MascotaModel;
import com.inube.veterinaria.repository.ChipRastreoRepository;
import com.inube.veterinaria.repository.MascotaRepository;
import com.inube.veterinaria.util.UtilConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChipRastreoService {

    private final ChipRastreoRepository repository;
    private final MascotaRepository mascotaRepository;

    public ChipRastreoModel guardar(ChipRastreoRequest request) {

        if (request.codigoChip() == null ||
                request.codigoChip().isBlank()) {
            throw new IllegalArgumentException(
                    UtilConstants.MSG15
            );
        }

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
                    "No se puede registrar un chip a una mascota inactiva"
            );
        }

        if (repository.findByCodigoChip(request.codigoChip()).isPresent()) {
            throw new IllegalArgumentException(
                    UtilConstants.MSG16
            );
        }

        ChipRastreoModel chip = new ChipRastreoModel();

        chip.setCodigoChip(request.codigoChip());
        chip.setFabricante(request.fabricante());
        // Fecha de implantación automática
        chip.setFechaImplantacion(LocalDateTime.now());

        chip.setMascota(mascota);
        chip.setEstado(UtilConstants.CODEPOS);

        return repository.save(chip);
    }

    public List<ChipRastreoModel> listar() {
        return repository.findByEstado(UtilConstants.CODEPOS);
    }

    public List<ChipRastreoModel> buscarPorMascota(Long idMascota) {

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
