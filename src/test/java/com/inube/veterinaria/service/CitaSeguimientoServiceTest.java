
package com.inube.veterinaria.service;
import com.inube.veterinaria.model.CitaSeguimientoModel;
import com.inube.veterinaria.model.VacunaModel;
import com.inube.veterinaria.dto.CitaSeguimientoRequest;
import com.inube.veterinaria.model.AdoptanteModel;
import com.inube.veterinaria.model.MascotaModel;
import com.inube.veterinaria.repository.CitaSeguimientoRepository;
import com.inube.veterinaria.repository.MascotaRepository;
import com.inube.veterinaria.repository.VacunaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaSeguimientoServiceTest {

    @Mock
    private CitaSeguimientoRepository citaRepository;

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private VacunaRepository vacunaRepository;

    @InjectMocks
    private CitaSeguimientoService citaService;

    private MascotaModel mascota;

    @BeforeEach
    void prepararDatos() {

        AdoptanteModel adoptante = new AdoptanteModel();
        adoptante.setEstado(1);

        mascota = new MascotaModel();
        mascota.setEstado(1);
        mascota.setAdoptante(adoptante);
    }

    @Test
    void noDebeProgramarCitaSiMascotaNoTieneVacunas() {

        Long idMascota = 3L;

        when(mascotaRepository.findById(idMascota))
                .thenReturn(Optional.of(mascota));

        when(vacunaRepository.findByMascota_IdMascotaAndEstado(
                idMascota, 1
        )).thenReturn(List.of());

        CitaSeguimientoRequest request =
                new CitaSeguimientoRequest(
                        idMascota,
                        LocalDateTime.now().plusDays(7),
                        "Revision veterinaria",
                        "Prueba sin vacunas"
                );

        IllegalArgumentException excepcion =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> citaService.guardar(request)
                );

        assertNotNull(excepcion.getMessage());

        verify(citaRepository, never()).save(any());
    }

    @Test
    void debeProgramarCitaSiMascotaTieneVacunas() {

        Long idMascota = 2L;

        when(mascotaRepository.findById(idMascota))
                .thenReturn(Optional.of(mascota));

        when(vacunaRepository.findByMascota_IdMascotaAndEstado(
                idMascota, 1
        )).thenReturn(List.of(new VacunaModel()));

        when(citaRepository.save(any(CitaSeguimientoModel.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CitaSeguimientoRequest request =
                new CitaSeguimientoRequest(
                        idMascota,
                        LocalDateTime.now().plusDays(7),
                        "Revision veterinaria de Rocky",
                        "Prueba de cita con vacunas"
                );

        CitaSeguimientoModel cita = citaService.guardar(request);

        assertNotNull(cita);
        assertEquals("PENDIENTE", cita.getEstatus());
        assertEquals(1, cita.getEstado());
        assertEquals(request.fechaCita(), cita.getFechaCita());
        assertEquals(request.motivo(), cita.getMotivo());

        verify(citaRepository, times(1))
                .save(any(CitaSeguimientoModel.class));
    }

}
