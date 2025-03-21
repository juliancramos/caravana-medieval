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
import java.util.List;

@RestController
@RequestMapping("/caravana")
public class CaravanaController {
    private final CaravanaService caravanaService;
    @Autowired
    public CaravanaController(CaravanaService caravanaService) {
        this.caravanaService = caravanaService;
    }

    @PostMapping("/crearCaravana")
    public ResponseEntity<Caravana> crearCaravana(@RequestBody CaravanaDTO caravanaDTO) {
        Caravana nuevaCaravana = caravanaService.crearCaravana(caravanaDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{nombre}")
                .buildAndExpand(nuevaCaravana.getNombre())
                .toUri();

        return ResponseEntity.created(location).body(nuevaCaravana);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caravana> getCaravana(@PathVariable Long id) {
        Caravana caravana = caravanaService.getCaravana(id);
        return ResponseEntity.ok(caravana);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Caravana>> listar() {
        return ResponseEntity.ok(caravanaService.getCaravanas());
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Caravana> actualizarCaravana(@PathVariable Long id,  @RequestBody CaravanaDTO nuevaCaravana){
        Caravana actualizada = caravanaService.actualizarCaravana(id, nuevaCaravana);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCaravana(@PathVariable Long id) {
        caravanaService.eliminarCaravana(id);
        return ResponseEntity.noContent().build();
    }
}
