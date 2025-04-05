package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.ServicesDTO;
import web.app.caravanamedieval.model.Services;
import web.app.caravanamedieval.service.ServicesService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/services")
public class ServicesController {

    private final ServicesService servicesService;

    @Autowired
    public ServicesController(ServicesService servicesService) {this.servicesService = servicesService;}

    @PostMapping("/create")
    public ResponseEntity<Services> createService(@RequestBody ServicesDTO serviceDTO) {
        Services newService = servicesService.createService(serviceDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newService.getIdService())
                .toUri();
        return ResponseEntity.created(location).body(newService);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Services>> getServices() {
        List<Services> services = servicesService.getServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Services> getService(@PathVariable Long id) {
        Services service = servicesService.getService(id);
        return ResponseEntity.ok(service);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Services> updateService(@PathVariable Long id, @RequestBody ServicesDTO updatedServiceDTO) {
        Services updatedService = servicesService.updateService(id, updatedServiceDTO);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        servicesService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
