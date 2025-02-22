package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.service.MapaService;

import java.net.URI;

@RestController
@RequestMapping("mapa")
public class MapaController {
    @Autowired
    private MapaService mapaService;

    @PostMapping("/crearMapa")
    public ResponseEntity<?> crearMapa(@RequestBody Mapa mapa) {
        try{
            Mapa nuevoMapa = mapaService.crearMapa(mapa);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{nombre}")
                    .buildAndExpand(nuevoMapa.getNombre())
                    .toUri();
            return ResponseEntity.created(location).body(nuevoMapa);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el mapa: " + e.getMessage());
        }
    }

    @PostMapping("agregarRegistros")
    public ResponseEntity<?> agregarRegistros() {
        try{
            for (int i = 1; i <= 3; i++){
                String nombre = "mapa" + i;
                String descripcion = "Este es el mapa " + i;
                mapaService.crearMapa(new Mapa(nombre, descripcion));
            }
            return ResponseEntity.ok("Se agregaron 3 registros correctamente");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar registros: " + e.getMessage());
        }
    }
}
