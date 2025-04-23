package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.CaravanDTO;
import web.app.caravanamedieval.mapper.CaravanMapper;
import web.app.caravanamedieval.model.Caravan;
import web.app.caravanamedieval.model.City;
import web.app.caravanamedieval.repository.CaravanRepository;
import web.app.caravanamedieval.repository.CityRepository;

import java.util.List;

@Service
public class CaravanServiceImpl implements CaravanService {

    private final CaravanRepository caravanRepository;
    private final CityRepository cityRepository;

    @Autowired
    public CaravanServiceImpl(CaravanRepository caravanRepository, CityRepository cityRepository) {
        this.caravanRepository = caravanRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public Caravan createCaravan(CaravanDTO caravanDTO) {
        City currentCity = cityRepository.findById(caravanDTO.getCurrentCityId())
                .orElseThrow(() -> new EntityNotFoundException("Ciudad con ID " + caravanDTO.getCurrentCityId() + " no encontrada"));

        Caravan caravan = CaravanMapper.INSTANCE.toEntity(caravanDTO);
        caravan.setCurrentCity(currentCity);
        return caravanRepository.save(caravan);
    }

    @Override
    public Caravan getCaravans(Long id) {
        return caravanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Caravana con ID " + id + " no encontrada"));
    }

    @Override
    public List<Caravan> getCaravans() {
        return caravanRepository.findAll();
    }

    @Override
    public Caravan updateCaravan(Long id, CaravanDTO caravanDTO) {
        Caravan caravan = caravanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Caravana con ID " + id + " no encontrada"));

        // Mapper actualiza los cambios directamente
       CaravanMapper.INSTANCE.updateEntity(caravan, caravanDTO);
        if (caravanDTO.getCurrentCityId() != null) {
            City currentCity = cityRepository.findById(caravanDTO.getCurrentCityId())
                    .orElseThrow(() -> new EntityNotFoundException("Ciudad con ID " + caravanDTO.getCurrentCityId() + " no encontrada"));
            caravan.setCurrentCity(currentCity);
        }
        return caravanRepository.save(caravan);
    }

    @Override
    public void deleteCaravan(Long id) {
        caravanRepository.deleteById(id);
    }
}
