package com.inube.veterinaria.repository;

import com.inube.veterinaria.model.MascotaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<MascotaModel, Long> {

    List<MascotaModel> findByEstado(Integer estado);

    List<MascotaModel> findByAdoptante_IdAdoptanteAndEstado(
            Long idAdoptante,
            Integer estado
    );
}