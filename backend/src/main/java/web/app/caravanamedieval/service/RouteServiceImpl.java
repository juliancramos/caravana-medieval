package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.RouteDTO;
import web.app.caravanamedieval.mapper.RouteMapper;
import web.app.caravanamedieval.model.Route;
import web.app.caravanamedieval.model.City;
import web.app.caravanamedieval.repository.RouteRepository;
import web.app.caravanamedieval.repository.CityRepository;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final CityRepository cityRepository;
    private final RouteMapper routeMapper;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, CityRepository cityRepository, RouteMapper routeMapper) {
        this.routeRepository = routeRepository;
        this.cityRepository = cityRepository;
        this.routeMapper = routeMapper;
    }

    @Override
    public Route createRoute(RouteDTO routeDTO) {
        City originCity = cityRepository.findById(routeDTO.getOriginCityId())
                .orElseThrow(() -> new EntityNotFoundException("Origin city not found with ID " + routeDTO.getOriginCityId()));
        City destinationCity = cityRepository.findById(routeDTO.getDestinationCityId())
                .orElseThrow(() -> new EntityNotFoundException("Destination city not found with ID " + routeDTO.getDestinationCityId()));

        Route route = routeMapper.toEntity(routeDTO);
        route.setOriginCity(originCity);
        route.setDestinationCity(destinationCity);

        return routeRepository.save(route);
    }

    @Override
    public Route getRoute(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route not found with ID " + id));
    }

    @Override
    public List<Route> getRoutes() {
        return routeRepository.findAll();
    }

    @Override
    public List<Route> getRoutesFromCity(long ciudadId) {
        return routeRepository.findByOriginCityIdCity(ciudadId);
    }

    @Override
    public List<Route> getRoutesToCity(long ciudadId) {
        return routeRepository.findByDestinationCityIdCity(ciudadId);
    }

    @Override
    @Transactional
    public Route updateRoute(Long id, RouteDTO routeDTO) {
        Route existingRoute = routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route not found with ID " + id));

        routeMapper.updateEntity(existingRoute, routeDTO);

        if (routeDTO.getOriginCityId() != 0) {
            City originCity = cityRepository.findById(routeDTO.getOriginCityId())
                    .orElseThrow(() -> new EntityNotFoundException("Origin city not found with ID " + routeDTO.getOriginCityId()));
            existingRoute.setOriginCity(originCity);
        }
        if (routeDTO.getDestinationCityId() != 0) {
            City destinationCity = cityRepository.findById(routeDTO.getDestinationCityId())
                    .orElseThrow(() -> new EntityNotFoundException("Destination city not found with ID " + routeDTO.getDestinationCityId()));
            existingRoute.setDestinationCity(destinationCity);
        }
        return routeRepository.save(existingRoute);
    }

    @Override
    public void deleteRoute(Long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route not found with ID " + id));
        routeRepository.delete(route);
    }
}
