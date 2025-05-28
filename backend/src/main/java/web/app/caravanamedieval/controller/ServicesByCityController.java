package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.ServiceForStoreDTO;
import web.app.caravanamedieval.dto.ServicesByCityDTO;
import web.app.caravanamedieval.model.Services;
import web.app.caravanamedieval.model.ServicesByCity;
import web.app.caravanamedieval.service.ServicesByCaravanServiceImpl;
import web.app.caravanamedieval.service.ServicesByCityServiceImpl;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/services-by-city")
public class ServicesByCityController {

    private final ServicesByCityServiceImpl servicesByCityService;
    private final ServicesByCaravanServiceImpl servicesByCaravanService;


    @Autowired
    public ServicesByCityController(ServicesByCityServiceImpl servicesByCityService, ServicesByCaravanServiceImpl servicesByCaravanService) {
        this.servicesByCityService = servicesByCityService;
        this.servicesByCaravanService = servicesByCaravanService;
    }


    @PreAuthorize("hasAnyRole('CARAVANERO')")
    @PostMapping("/buy")
    public ResponseEntity<Void> buyService(@RequestParam Long gameId, @RequestParam Long serviceId) {
        servicesByCityService.buyService(gameId, serviceId);
        return ResponseEntity.ok().build();
    }







    @PostMapping("/assign")
    public ResponseEntity<ServicesByCity> assignServiceToCity(@RequestBody ServicesByCityDTO dto) {
        ServicesByCity assignment = servicesByCityService.assignServiceToCity(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{serviceId}/{cityId}")
                .buildAndExpand(dto.getServiceId(), dto.getCityId())
                .toUri();
        return ResponseEntity.created(location).body(assignment);
    }

    @PutMapping("/update")
    public ResponseEntity<ServicesByCity> updateAssignment(@RequestBody ServicesByCityDTO dto) {
        ServicesByCity updatedAssignment = servicesByCityService.updateAssignment(dto);
        return ResponseEntity.ok(updatedAssignment);
    }

    @DeleteMapping("/remove/services/{serviceId}/cities/{cityId}")
    public ResponseEntity<Void> removeAssignment(@PathVariable Long serviceId, @PathVariable Long cityId) {
        servicesByCityService.removeAssignment(cityId, serviceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/service/{serviceId}/city/{cityId}")
    public ResponseEntity<ServicesByCity> getAssignment(@PathVariable Long serviceId, @PathVariable Long cityId) {
        return ResponseEntity.ok(servicesByCityService.getAssignment(cityId, serviceId));
    }

    @GetMapping("/services/city/{id}")
    public ResponseEntity<List<ServicesByCity>> getServicesByCityId(@PathVariable Long id) {
        return ResponseEntity.ok(servicesByCityService.getServicesByCityId(id));
    }

    @GetMapping("/available/city/{id}")
    public ResponseEntity<List<ServiceForStoreDTO>> getAvailableServices(@PathVariable Long id) {
        return ResponseEntity.ok(servicesByCityService.getAvailableServicesByCity(id));
    }


    @GetMapping("/list")
    public ResponseEntity<List<ServicesByCity>> listAll() {
        return ResponseEntity.ok(servicesByCityService.listAll());
    }
}
