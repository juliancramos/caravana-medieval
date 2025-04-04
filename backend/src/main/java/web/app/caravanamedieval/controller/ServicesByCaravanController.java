package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.ServicesByCaravanDTO;
import web.app.caravanamedieval.model.ServicesByCaravan;
import web.app.caravanamedieval.service.ServicesByCaravanServiceImpl;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/services-by-caravan")
public class ServicesByCaravanController {

    private final ServicesByCaravanServiceImpl servicesByCaravanService;

    @Autowired
    public ServicesByCaravanController(ServicesByCaravanServiceImpl servicesByCaravanService) {
        this.servicesByCaravanService = servicesByCaravanService;
    }

    @PostMapping("/assign")
    public ResponseEntity<ServicesByCaravan> assignServiceToCaravan(@RequestBody ServicesByCaravanDTO dto) {
        ServicesByCaravan assignment = servicesByCaravanService.assignServiceToCaravan(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{serviceId}/{caravanId}")
                .buildAndExpand(dto.getServiceId(), dto.getCaravanId())
                .toUri();

        return ResponseEntity.created(location).body(assignment);
    }

    @DeleteMapping("/remove/services/{serviceId}/caravans/{caravanId}")
    public ResponseEntity<Void> removeAssignment(@PathVariable Long serviceId, @PathVariable Long caravanId) {
        servicesByCaravanService.removeAssignment(serviceId, caravanId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/service/{serviceId}/caravan/{caravanId}")
    public ResponseEntity<ServicesByCaravan> getService(@PathVariable Long serviceId, @PathVariable Long caravanId) {
        return ResponseEntity.ok(servicesByCaravanService.getService(serviceId, caravanId));
    }

    @GetMapping("/services/caravan/{id}")
    public ResponseEntity<List<ServicesByCaravan>> getServicesByCaravanId(@PathVariable Long id) {
        return ResponseEntity.ok(servicesByCaravanService.getServicesByCaravanId(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ServicesByCaravan>> listServicesByCaravan() {
        return ResponseEntity.ok(servicesByCaravanService.listServicesByCaravan());
    }

    @PutMapping("/update")
    public ResponseEntity<ServicesByCaravan> updateAssignment(@RequestBody ServicesByCaravanDTO dto) {
        ServicesByCaravan updatedAssignment = servicesByCaravanService.updateAssignment(dto);
        return ResponseEntity.ok(updatedAssignment);
    }
}
