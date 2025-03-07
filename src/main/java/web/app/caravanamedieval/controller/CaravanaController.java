package web.app.caravanamedieval.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.CaravanaDTO;
import web.app.caravanamedieval.model.Caravana;
import web.app.caravanamedieval.service.CaravanaService;

import java.net.URI;

@RestController
@RequestMapping("/caravana")
public class CaravanaController {
    @Autowired
    private CaravanaService caravanaService;

    @PostMapping("/crearCaravana")
    public ResponseEntity<?> crearCaravana(@RequestBody CaravanaDTO caravanaDTO){
        try{
            Caravana nuevaCaravana = caravanaService.crearCaravana(caravanaDTO);

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
                caravanaService.crearCaravana(new CaravanaDTO(nombre, 30.0 + i, 50.0+i, 1000L, 100));
            }
            return ResponseEntity.ok("Se agregaron 100 registros correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar registros: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getCaravana(@PathVariable Long id){
        try{
            Caravana caravana = caravanaService.getCaravana(id);
            if(caravana == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Caravana no encontrada");
            }
            return ResponseEntity.ok(caravana);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> getCaravanas(){
        try{
            return ResponseEntity.ok(caravanaService.getCaravanas());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCaravana(@PathVariable Long id, @RequestBody CaravanaDTO nuevaCaravana){
        try{
            Caravana actualizada = caravanaService.actualizarCaravana(id, nuevaCaravana);
            return ResponseEntity.ok(actualizada);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la caravana: " + e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCaravanaCompleta(@PathVariable Long id, @RequestBody CaravanaDTO nuevaCaravana){
        try{
            Caravana actualizada = caravanaService.actualizarCaravana(id, nuevaCaravana);
            return ResponseEntity.ok(actualizada);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la caravana: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarCaravana(@PathVariable Long id){
        try{
            caravanaService.eliminarCaravana(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Error al eliminar la caravana: " + e.getMessage());
        }
    }
}
