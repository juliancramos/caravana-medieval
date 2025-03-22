package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.RouteDTO;
import web.app.caravanamedieval.model.Route;

import java.util.List;

public interface RouteService {
    Route createRoute(RouteDTO routeDTO);
    List<Route> getRoutesFromCity(long ciudadId);
    List<Route> getRoutesToCity(long ciudadId);
}
