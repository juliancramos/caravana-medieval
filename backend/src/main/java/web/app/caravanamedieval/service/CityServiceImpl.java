package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.CityDTO;
import web.app.caravanamedieval.mapper.CityMapper;
import web.app.caravanamedieval.model.City;
import web.app.caravanamedieval.model.Map;
import web.app.caravanamedieval.repository.CityRepository;
import web.app.caravanamedieval.repository.MapRepository;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final MapRepository mapRepository;
    private final CityMapper cityMapper;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, MapRepository mapRepository,
                           CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.mapRepository = mapRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    @Transactional
    public City createCity(CityDTO cityDTO) {
        City city = cityMapper.toEntity(cityDTO);

        if (cityDTO.getMapId() != null) {
            Map map = mapRepository.findById(cityDTO.getMapId())
                    .orElseThrow(() -> new EntityNotFoundException("Mapa con ID " + cityDTO.getMapId() + " no encontrado"));
            city.setMap(map);
        }

        return cityRepository.save(city);
    }


    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public City getCity(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ciudad con ID " + id + " no encontrada"));
    }

    @Override
    @Transactional
    public City updateCity(Long id, CityDTO actualizado) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ciudad con ID " + id + " no encontrada"));

        if (actualizado.getName() != null) {
            city.setName(actualizado.getName());
        }
        if (actualizado.getEntryTax() != null) {
            city.setEntryTax(actualizado.getEntryTax());
        }
        if (actualizado.getMapId() != null) {
            Map map = mapRepository.findById(actualizado.getMapId())
                    .orElseThrow(() -> new EntityNotFoundException("Mapa con ID " + actualizado.getMapId() + " no encontrado"));
            city.setMap(map);
        }

        return cityRepository.save(city);
    }

    @Override
    @Transactional
    public void deleteCity(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new EntityNotFoundException("Ciudad con ID " + id + " no encontrada.");
        }
        cityRepository.deleteById(id);
    }
}

