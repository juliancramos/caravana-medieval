package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Route> createRoute(@RequestBody RouteDTO routeDTO) {
        Route newRoute = routeService.createRoute(routeDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newRoute.getIdRoute())
                .toUri();
        return ResponseEntity.created(location).body(newRoute);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getRoute(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.getRoute(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Route>> getRoutes() {
        List<Route> routes = routeService.getRoutes();
        return ResponseEntity.ok(routes);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable Long id, @RequestBody RouteDTO routeDTO) {
        Route updatedRoute = routeService.updateRoute(id, routeDTO);
        return ResponseEntity.ok(updatedRoute);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
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
