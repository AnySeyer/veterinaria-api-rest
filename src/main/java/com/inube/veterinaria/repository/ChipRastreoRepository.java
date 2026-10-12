
package com.inube.veterinaria.repository;

import com.inube.veterinaria.model.ChipRastreoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChipRastreoRepository
        extends JpaRepository<ChipRastreoModel, Long> {

    List<ChipRastreoModel> findByEstado(Integer estado);

    List<ChipRastreoModel> findByMascota_IdMascotaAndEstado(
            Long idMascota,
            Integer estado
    );

    Optional<ChipRastreoModel> findByCodigoChip(String codigoChip);
}
