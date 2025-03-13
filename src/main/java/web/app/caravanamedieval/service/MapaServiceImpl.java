package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.MapaDTO;
import web.app.caravanamedieval.mapper.MapaMapper;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.repository.MapaRepository;

@Service
public class MapaServiceImpl implements MapaService {
    @Autowired
    private MapaRepository mapaRepository;



    @Override
    public Mapa crearMapa(MapaDTO mapaDTO) {
        Mapa mapa = MapaMapper.INSTANCE.toEntity(mapaDTO);
        return mapaRepository.save(mapa);
    }

    public Mapa getMapa(Long id) {
        return mapaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mapa no encontrado"));
    }

    public void asignarCiudadAMapa(Mapa mapa, Ciudad ciudad) {
        // Verificar que la ciudad no esté en el mapa
        if (!mapa.getCiudades().contains(ciudad)) {
            mapa.getCiudades().add(ciudad);
            mapaRepository.save(mapa);
        }
    }



}
