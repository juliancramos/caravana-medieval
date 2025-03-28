package web.app.caravanamedieval.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.RouteDTO;
import web.app.caravanamedieval.model.Route;
import web.app.caravanamedieval.service.RouteService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Route> createRoute(@RequestBody RouteDTO routeDTO) {
        Route route = routeService.createRoute(routeDTO);

        // URI del recurso creado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(route.getIdRoute())
                .toUri();

        return ResponseEntity.created(location).body(route);
    }

    @GetMapping("/from/{cityId}")
    public ResponseEntity<List<Route>> getConnectionsFrom(@PathVariable Long cityId) {
        List<Route> routes = routeService.getRoutesFromCity(cityId);
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/to/{cityId}")
    public ResponseEntity<List<Route>> getConnectionsTo(@PathVariable Long cityId) {
        List<Route> routes = routeService.getRoutesToCity(cityId);
        return ResponseEntity.ok(routes);
    }
}
