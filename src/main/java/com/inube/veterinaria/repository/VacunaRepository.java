
package com.inube.veterinaria.repository;

import com.inube.veterinaria.model.VacunaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacunaRepository extends JpaRepository<VacunaModel, Long> {

    // Consultar todas las vacunas activas
    List<VacunaModel> findByEstado(Integer estado);

    // Consultar las vacunas activas de una mascota
    List<VacunaModel> findByMascota_IdMascotaAndEstado(
            Long idMascota,
            Integer estado
    );

    // Obtener mascotas vacunadas y cantidad de vacunas,
    // ordenadas por especie y nombre de mascota
    @Query("""
            SELECT v.mascota.especie,
                   v.mascota.idMascota,
                   v.mascota.nombre,
                   COUNT(v)
            FROM VacunaModel v
            WHERE v.estado = 1
              AND v.mascota.estado = 1
            GROUP BY v.mascota.especie,
                     v.mascota.idMascota,
                     v.mascota.nombre
            ORDER BY v.mascota.especie ASC,
                     v.mascota.nombre ASC
            """)
    List<Object[]> reporteMascotasVacunadas();
}
