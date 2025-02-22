package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.model.Caravana;
import web.app.caravanamedieval.service.CaravanaService;

import java.net.URI;

@RestController
@RequestMapping("/caravana")
public class CaravanaController {
    @Autowired
    private CaravanaService caravanaService;

    @PostMapping("/crearCaravana")
    public ResponseEntity<?> crearCaravana(@RequestBody Caravana caravana){
        try{
            Caravana nuevaCaravana = caravanaService.crearCaravana(caravana);

            // Crear la URI para retornar la ubicación de la caravana
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{nombre}")
                    .buildAndExpand(nuevaCaravana.getNombre())
                    .toUri();
            return ResponseEntity.created(location).body(nuevaCaravana);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la caravana: " + e.getMessage());
        }
    }

    @PostMapping("/agregarRegistros")
    public ResponseEntity<String> agregarRegistros() {
        try {
            for (int i = 0; i < 100; i++) {
                String nombre = "nombre" + i;
                caravanaService.crearCaravana(new Caravana(nombre, 30 + i, 50+i, 1000, 100));
            }
            return ResponseEntity.ok("Se agregaron 100 registros correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar registros: " + e.getMessage());
        }
    }

}
