package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.repository.MapaRepository;

@Service
public class MapaServiceImpl implements MapaService {
    @Autowired
    private MapaRepository mapaRepository;

    @Autowired
    private CiudadService ciudadService;

    @Override
    public Mapa crearMapa(Mapa mapa) {
        return mapaRepository.save(mapa);
    }

    public Mapa getMapa(Long id) {
        return mapaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mapa no encontrado"));
    }

    public void asignarCiudadAMapa(Long mapaId, Long ciudadId) {
        Mapa mapa = getMapa(mapaId);
        Ciudad ciudad = ciudadService.getCiudad(ciudadId);
        mapa.getCiudades().add(ciudad);
        mapaRepository.save(mapa);
    }


}
