
package com.inube.veterinaria.service;

import com.inube.veterinaria.model.AdoptanteModel;
import com.inube.veterinaria.repository.AdoptanteRepository;
import com.inube.veterinaria.util.UtilConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptanteService {

    private final AdoptanteRepository repository;

    public AdoptanteModel guardar(AdoptanteModel adoptante) {

        adoptante.setIdAdoptante(null);
        adoptante.setEstado(UtilConstants.CODEPOS);

        return repository.save(adoptante);
    }

    public List<AdoptanteModel> listar() {

        return repository.findByEstado(UtilConstants.CODEPOS);
    }

    public AdoptanteModel buscarPorId(Long id) {

        AdoptanteModel adoptante = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(UtilConstants.MSG5)
                );

        if (adoptante.getEstado() == null ||
                adoptante.getEstado() != UtilConstants.CODEPOS) {

            throw new RuntimeException(UtilConstants.MSG6);
        }

        return adoptante;
    }

    public AdoptanteModel actualizar(Long id, AdoptanteModel request) {

        AdoptanteModel adoptante = buscarPorId(id);

        adoptante.setNombre(request.getNombre());
        adoptante.setApellido(request.getApellido());
        adoptante.setTelefono(request.getTelefono());
        adoptante.setCorreo(request.getCorreo());
        adoptante.setDireccion(request.getDireccion());

        return repository.save(adoptante);
    }

    public void eliminar(Long id) {

        AdoptanteModel adoptante = buscarPorId(id);

        adoptante.setEstado(UtilConstants.CODENEG);

        repository.save(adoptante);
    }
}
