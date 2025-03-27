package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.MapDTO;
import web.app.caravanamedieval.mapper.MapMapper;
import web.app.caravanamedieval.model.City;
import web.app.caravanamedieval.model.Map;
import web.app.caravanamedieval.repository.CityRepository;
import web.app.caravanamedieval.repository.MapRepository;

import java.util.List;

@Service
public class MapServiceImpl implements MapService {

    private final MapRepository mapRepository;
    private final CityRepository cityRepository;
    private final MapMapper mapMapper;

    @Autowired
    public MapServiceImpl(MapRepository mapRepository, CityRepository cityRepository, MapMapper mapMapper) {
        this.mapRepository = mapRepository;
        this.cityRepository = cityRepository;
        this.mapMapper = mapMapper;
    }

    @Override
    public Map createMap(MapDTO mapDTO) {
        Map map = mapMapper.toEntity(mapDTO);
        return mapRepository.save(map);
    }

    @Override
    public List<Map> getMaps() {
        return mapRepository.findAll();
    }

    @Override
    public Map getMap(Long id) {
        return mapRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mapa con ID " + id + " no encontrado"));
    }

    @Override
    public Map updateMap(Long id, MapDTO mapDTO) {
        Map mapExistente = getMap(id);  // Aprovechar el método existente
        mapMapper.updateEntity(mapExistente, mapDTO);
        return mapRepository.save(mapExistente);
    }

    @Override
    public void deleteMap(Long id) {
        Map map = getMap(id);  // Validar existencia
        mapRepository.delete(map);
    }


    @Override
    public void assignCityToMap(Long mapaId, Long ciudadId) {
        Map map = mapRepository.findById(mapaId)
                .orElseThrow(() -> new EntityNotFoundException("Mapa no encontrado"));

        City city = cityRepository.findById(ciudadId)
                .orElseThrow(() -> new EntityNotFoundException("Ciudad no encontrada"));

        if (!map.getCities().contains(city)) {
            map.addCity(city);
            mapRepository.save(map);
        } else {
            throw new IllegalArgumentException("La ciudad ya está asignada a este mapa");
        }
    }
}
