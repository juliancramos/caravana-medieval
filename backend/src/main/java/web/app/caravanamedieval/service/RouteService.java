package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.RouteDTO;
import web.app.caravanamedieval.model.Route;

import java.util.List;

public interface RouteService {
    Route createRoute(RouteDTO routeDTO);
    Route getRoute(Long id);
    List<Route> getRoutes();
    Route updateRoute(Long id, RouteDTO routeDTO);
    void deleteRoute(Long id);
    List<Route> getRoutesFromCity(long ciudadId);
    List<Route> getRoutesToCity(long ciudadId);
}
