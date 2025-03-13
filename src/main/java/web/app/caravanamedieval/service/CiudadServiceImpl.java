package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.CiudadDTO;
import web.app.caravanamedieval.mapper.CiudadMapper;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.repository.CiudadRepository;

@Service
public class CiudadServiceImpl implements CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;


    @Override
    public Ciudad crearCiudad(Ciudad ciudad) {
        return ciudadRepository.save(ciudad);
    }



    public Ciudad getCiudad(Long id) {
        return ciudadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ciudad no encontrada"));
    }

}
