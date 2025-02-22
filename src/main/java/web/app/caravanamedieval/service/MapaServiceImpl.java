package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.repository.MapaRepository;

@Service
public class MapaServiceImpl implements MapaService {
    @Autowired
    private MapaRepository mapaRepository;

    @Override
    public Mapa crearMapa(Mapa mapa) {
        return mapaRepository.save(mapa);
    }

    public Mapa getMapa(Long id) {
        return mapaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mapa no encontrado"));
    }

}
