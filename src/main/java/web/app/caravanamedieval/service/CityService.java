package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.CityDTO;
import web.app.caravanamedieval.model.City;

import java.util.List;

public interface CityService {
    City createCity(CityDTO cityDTO);
    List<City> getCities();
    City getCity(Long id);
    City updateCity(Long id, CityDTO actualizado);
    void deleteCity(Long id);
}
