package web.app.caravanamedieval.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.RutaDTO;
import web.app.caravanamedieval.mapper.RutaMapper;
import web.app.caravanamedieval.model.Ruta;
import web.app.caravanamedieval.service.RutaService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ruta")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @PostMapping
    public ResponseEntity<Ruta> crearRuta(@RequestBody RutaDTO rutaDTO) {
        Ruta ruta = rutaService.crearRuta(rutaDTO);

        // URI del recurso creado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ruta.getIdRuta())
                .toUri();

        return ResponseEntity.created(location).body(ruta);
    }

    @GetMapping("/desde/{ciudadId}")
    public ResponseEntity<List<Ruta>> obtenerConexionesDesde(@PathVariable Long ciudadId) {
        List<Ruta> rutas = rutaService.getRutaDesdeCiudad(ciudadId);
        return ResponseEntity.ok(rutas);
    }

    @GetMapping("/hacia/{ciudadId}")
    public ResponseEntity<List<Ruta>> obtenerConexionesHacia(@PathVariable Long ciudadId) {
        List<Ruta> rutas = rutaService.getRutaHaciaCiudad(ciudadId);
        return ResponseEntity.ok(rutas);
    }
}
