package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.CiudadDTO;
import web.app.caravanamedieval.mapper.CiudadMapper;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.service.CiudadService;
import web.app.caravanamedieval.service.MapaService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ciudad")
public class CiudadController {

    private final CiudadService ciudadService;

    @Autowired
    public CiudadController(CiudadService ciudadService) {
        this.ciudadService = ciudadService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Ciudad> crearCiudad(@RequestBody CiudadDTO ciudadDTO) {
        Ciudad nuevaCiudad = ciudadService.crearCiudad(ciudadDTO);

        // Crear la URI para retornar la ubicación de la ciudad
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevaCiudad.getIdCiudad())
                .toUri();

        return ResponseEntity.created(location).body(nuevaCiudad);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CiudadDTO>> listar() {
        List<CiudadDTO> ciudades = ciudadService.listarTodas();
        return ResponseEntity.ok(ciudades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CiudadDTO> obtenerCiudad(@PathVariable Long id) {
        CiudadDTO ciudad = ciudadService.obtenerCiudad(id);
        return ResponseEntity.ok(ciudad);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<CiudadDTO> actualizarCiudad(@PathVariable Long id, @RequestBody CiudadDTO ciudadDTO) {
        CiudadDTO ciudadActualizada = ciudadService.actualizarCiudad(id, ciudadDTO);
        return ResponseEntity.ok(ciudadActualizada);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCiudad(@PathVariable Long id) {
        ciudadService.eliminarCiudad(id);
        return ResponseEntity.noContent().build();
    }
}
