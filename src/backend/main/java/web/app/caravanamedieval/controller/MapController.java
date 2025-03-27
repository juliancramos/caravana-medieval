package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.MapDTO;
import web.app.caravanamedieval.model.Map;
import web.app.caravanamedieval.service.MapService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("map")
public class MapController {

    private final MapService mapService;
    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map> createMap(@RequestBody MapDTO mapDTO) {
        Map newMap = mapService.createMap(mapDTO);

        // Crear la URI para retornar la ubicación del recurso creado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newMap.getIdMap())
                .toUri();

        return ResponseEntity.created(location).body(newMap);
    }

    @GetMapping("/maps")
    public ResponseEntity<List<Map>> getMaps() {
        List<Map> maps = mapService.getMaps();
        return ResponseEntity.ok(maps);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map> getMap(@PathVariable Long id) {
        Map map = mapService.getMap(id);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map> updateMap(@PathVariable Long id, @RequestBody MapDTO mapDTO) {
        Map updateMap = mapService.updateMap(id, mapDTO);
        return ResponseEntity.ok(updateMap);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMap(@PathVariable Long id) {
        mapService.deleteMap(id);
        return ResponseEntity.noContent().build();
    }




    @PostMapping("/assign/{mapId}/city/{cityId}")
    public ResponseEntity<String> assignCity(@PathVariable Long mapId, @PathVariable Long cityId) {
        mapService.assignCityToMap(mapId, cityId);
        return ResponseEntity.ok("City assigned to the map correctly");
    }





}
