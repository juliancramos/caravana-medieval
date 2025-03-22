package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.CaravanDTO;
import web.app.caravanamedieval.model.Caravan;
import web.app.caravanamedieval.service.CaravanService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/caravan")
public class CaravanController {
    private final CaravanService caravanService;
    @Autowired
    public CaravanController(CaravanService caravanService) {
        this.caravanService = caravanService;
    }

    @PostMapping("/create")
    public ResponseEntity<Caravan> createCaravan(@RequestBody CaravanDTO caravanDTO) {
        Caravan newCaravan = caravanService.createCaravan(caravanDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{nombre}")
                .buildAndExpand(newCaravan.getName())
                .toUri();

        return ResponseEntity.created(location).body(newCaravan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caravan> getCaravan(@PathVariable Long id) {
        Caravan caravan = caravanService.getCaravans(id);
        return ResponseEntity.ok(caravan);
    }

    @GetMapping("/caravans")
    public ResponseEntity<List<Caravan>> getCaravans() {
        return ResponseEntity.ok(caravanService.getCaravans());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Caravan> updateCaravana(@PathVariable Long id, @RequestBody CaravanDTO newCaravanDTO){
        Caravan updated = caravanService.updateCaravan(id, newCaravanDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCaravana(@PathVariable Long id) {
        caravanService.deleteCaravan(id);
        return ResponseEntity.noContent().build();
    }
}
