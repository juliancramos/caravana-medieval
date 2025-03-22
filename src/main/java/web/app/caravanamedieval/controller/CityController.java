package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.CityDTO;
import web.app.caravanamedieval.model.City;
import web.app.caravanamedieval.service.CityService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/create")
    public ResponseEntity<City> createCity(@RequestBody CityDTO cityDTO) {
        City newCity = cityService.createCity(cityDTO);

        // Crear la URI para retornar la ubicación de la ciudad
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCity.getIdCity())
                .toUri();

        return ResponseEntity.created(location).body(newCity);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getCities() {
        List<City> cities = cityService.getCities();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCity(@PathVariable Long id) {
        City city = cityService.getCity(id);
        return ResponseEntity.ok(city);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody CityDTO cityDTO) {
        City cityActualizada = cityService.updateCity(id, cityDTO);
        return ResponseEntity.ok(cityActualizada);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
