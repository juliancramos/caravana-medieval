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
import web.app.caravanamedieval.service.CiudadService;

import java.net.URI;

@RestController
@RequestMapping("/ciudad")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    @PostMapping("/crearCiudad")
    public ResponseEntity<?> crearCiudad(@RequestBody Ciudad ciudad) {
        try {
            Ciudad nuevaCiudad = ciudadService.crearCiudad(ciudad);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{nombre}")
                    .buildAndExpand(nuevaCiudad.getNombre())
                    .toUri();
            return ResponseEntity.created(location).body(nuevaCiudad);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la ciudad: " + e.getMessage());
        }
    }

    @PostMapping("agregarRegistros")
    public ResponseEntity<?> agregarRegistros() {
        try{
            for (int i = 0; i < 15; i++){
                String nombre = "ciudad" + i;
                ciudadService.crearCiudad(new Ciudad(nombre, 7+i));
            }
            return ResponseEntity.ok("Se agregaron 15 registros correctamente");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar registros: " + e.getMessage());
        }
    }
}
