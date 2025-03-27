package web.app.caravanamedieval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.RouteDTO;
import web.app.caravanamedieval.mapper.RouteContext;
import web.app.caravanamedieval.mapper.RouteMapper;
import web.app.caravanamedieval.model.City;
import web.app.caravanamedieval.model.Route;
import web.app.caravanamedieval.repository.RouteRepository;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final CityService cityService;
    private final RouteMapper routeMapper;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, CityService cityService, RouteMapper routeMapper) {
        this.routeRepository = routeRepository;
        this.cityService = cityService;
        this.routeMapper = routeMapper;
    }

    @Override
    public Route createRoute(RouteDTO routeDTO) {
        City cityOrigen = cityService.getCity(routeDTO.getOriginCityId());
        City cityDestino = cityService.getCity(routeDTO.getDestinationCityId());

        RouteContext context = new RouteContext(cityOrigen, cityDestino);

        Route route = routeMapper.toEntity(routeDTO, context);
        return routeRepository.save(route);
    }

    @Override
    public List<Route> getRoutesFromCity(long ciudadId) {
        return routeRepository.findByOriginCityIdCity(ciudadId);
    }

    @Override
    public List<Route> getRoutesToCity(long ciudadId) {
        return routeRepository.findByDestinationCityIdCity(ciudadId);
    }
}
