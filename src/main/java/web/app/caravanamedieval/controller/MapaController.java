package web.app.caravanamedieval.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.MapaDTO;
import web.app.caravanamedieval.mapper.MapaMapper;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.service.CiudadService;
import web.app.caravanamedieval.service.MapaService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("mapa")
public class MapaController {

    private final MapaService mapaService;
    @Autowired
    public MapaController(MapaService mapaService) {
        this.mapaService = mapaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Mapa> crearMapa(@RequestBody MapaDTO mapaDTO) {
        Mapa nuevoMapa = mapaService.crearMapa(mapaDTO);

        // Crear la URI para retornar la ubicación del recurso creado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevoMapa.getIdMapa())
                .toUri();

        return ResponseEntity.created(location).body(nuevoMapa);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Mapa>> listar() {
        List<Mapa> mapas = mapaService.listarTodos();
        return ResponseEntity.ok(mapas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mapa> obtenerMapa(@PathVariable Long id) {
        Mapa mapa = mapaService.getMapa(id);
        return ResponseEntity.ok(mapa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mapa> actualizarMapa(@PathVariable Long id, @RequestBody MapaDTO mapaDTO) {
        Mapa mapaActualizado = mapaService.actualizarMapa(id, mapaDTO);
        return ResponseEntity.ok(mapaActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMapa(@PathVariable Long id) {
        mapaService.eliminarMapa(id);
        return ResponseEntity.noContent().build();
    }




    @PostMapping("/{mapaId}/ciudades/{ciudadId}")
    public ResponseEntity<String> asignarCiudad(@PathVariable Long mapaId, @PathVariable Long ciudadId) {
        mapaService.asignarCiudadAMapa(mapaId, ciudadId);
        return ResponseEntity.ok("Ciudad asignada al mapa correctamente");
    }





}
