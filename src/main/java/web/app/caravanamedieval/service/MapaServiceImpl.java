package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.MapaDTO;
import web.app.caravanamedieval.mapper.MapaMapper;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.repository.CiudadRepository;
import web.app.caravanamedieval.repository.MapaRepository;

import java.util.List;

@Service
public class MapaServiceImpl implements MapaService {

    private final MapaRepository mapaRepository;
    private final CiudadRepository ciudadRepository;
    private final MapaMapper mapaMapper;

    @Autowired
    public MapaServiceImpl(MapaRepository mapaRepository, CiudadRepository ciudadRepository, MapaMapper mapaMapper) {
        this.mapaRepository = mapaRepository;
        this.ciudadRepository = ciudadRepository;
        this.mapaMapper = mapaMapper;
    }

    @Override
    public Mapa crearMapa(MapaDTO mapaDTO) {
        Mapa mapa = mapaMapper.toEntity(mapaDTO);
        return mapaRepository.save(mapa);
    }

    @Override
    public List<Mapa> listarTodos() {
        return mapaRepository.findAll();
    }

    @Override
    public Mapa getMapa(Long id) {
        return mapaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mapa con ID " + id + " no encontrado"));
    }

    @Override
    public Mapa actualizarMapa(Long id, MapaDTO mapaDTO) {
        Mapa mapaExistente = getMapa(id);  // Aprovechar el método existente
        mapaMapper.updateEntity(mapaExistente, mapaDTO);
        return mapaRepository.save(mapaExistente);
    }

    @Override
    public void eliminarMapa(Long id) {
        Mapa mapa = getMapa(id);  // Validar existencia
        mapaRepository.delete(mapa);
    }


    @Override
    public void asignarCiudadAMapa(Long mapaId, Long ciudadId) {
        Mapa mapa = mapaRepository.findById(mapaId)
                .orElseThrow(() -> new EntityNotFoundException("Mapa no encontrado"));

        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new EntityNotFoundException("Ciudad no encontrada"));

        if (!mapa.getCiudades().contains(ciudad)) {
            mapa.addCiudad(ciudad);
            mapaRepository.save(mapa);
        } else {
            throw new IllegalArgumentException("La ciudad ya está asignada a este mapa");
        }
    }
}
