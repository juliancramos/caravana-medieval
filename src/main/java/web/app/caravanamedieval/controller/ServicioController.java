package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.app.caravanamedieval.model.Servicio;
import web.app.caravanamedieval.service.ServicioService;

import java.util.List;

@RestController
@RequestMapping("/servicio")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;


    @GetMapping
    public ResponseEntity<List<Servicio>> getAllServicios() {
        List<Servicio> servicios = servicioService.findAllServicios();
        return new ResponseEntity<>(servicios, HttpStatus.OK);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable Integer id) {
        Servicio servicio = servicioService.findServicioById(id);
        return servicio != null ? new ResponseEntity<>(servicio, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarServicios() {
        return ResponseEntity.ok(servicioService.findAllServicios());
    }

    
    @PostMapping("/crearServicio")
    public ResponseEntity<Servicio> createServicio(@RequestBody Servicio servicio) {
        Servicio newServicio = servicioService.saveServicio(servicio);
        return new ResponseEntity<>(newServicio, HttpStatus.CREATED);
    }

    // Actualizar un servicio
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> updateServicio(@PathVariable Integer id, @RequestBody Servicio servicio) {
        servicio.setIdServicio(id);
        Servicio updatedServicio = servicioService.updateServicio(servicio);
        return updatedServicio != null ? new ResponseEntity<>(updatedServicio, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Eliminar un servicio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable Integer id) {
        boolean isDeleted = servicioService.deleteServicio(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
