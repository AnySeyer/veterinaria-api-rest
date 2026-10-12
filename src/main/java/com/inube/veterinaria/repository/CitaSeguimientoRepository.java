
package com.inube.veterinaria.repository;

import com.inube.veterinaria.model.CitaSeguimientoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitaSeguimientoRepository
        extends JpaRepository<CitaSeguimientoModel, Long> {

    List<CitaSeguimientoModel> findByEstado(Integer estado);

    List<CitaSeguimientoModel> findByMascota_IdMascotaAndEstado(
            Long idMascota,
            Integer estado
    );
}
