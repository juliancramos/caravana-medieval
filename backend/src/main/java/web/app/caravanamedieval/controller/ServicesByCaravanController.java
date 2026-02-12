package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.ActiveServiceDTO;
import web.app.caravanamedieval.dto.ActiveServiceEffectDTO;
import web.app.caravanamedieval.dto.ServicesByCaravanDTO;
import web.app.caravanamedieval.model.ServicesByCaravan;
import web.app.caravanamedieval.repository.ServicesByCaravanRepository;
import web.app.caravanamedieval.service.ServicesByCaravanServiceImpl;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/services-by-caravan")
public class ServicesByCaravanController {

    private final ServicesByCaravanServiceImpl servicesByCaravanService;
    private final ServicesByCaravanRepository servicesByCaravanRepository;

    @Autowired
    public ServicesByCaravanController(ServicesByCaravanServiceImpl servicesByCaravanService, ServicesByCaravanRepository servicesByCaravanRepository) {
        this.servicesByCaravanService = servicesByCaravanService;
        this.servicesByCaravanRepository = servicesByCaravanRepository;
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

    @GetMapping("/active/caravan/{id}")
    public ResponseEntity<List<ActiveServiceDTO>> getActiveServices(@PathVariable Long id) {
        return ResponseEntity.ok(servicesByCaravanService.getActiveServicesByCaravanId(id));
    }

    @GetMapping("/effects/caravan/{caravanId}")
    public ResponseEntity<List<ActiveServiceEffectDTO>> getActiveServiceEffects(@PathVariable Long caravanId) {
        List<ServicesByCaravan> activeServices = servicesByCaravanRepository.findByCaravan_IdCaravan(caravanId);

        List<ActiveServiceEffectDTO> effects = activeServices.stream()
                .filter(s -> s.getServices().getName().equalsIgnoreCase("guardias")
                        || s.getServices().getName().equalsIgnoreCase("mejorar velocidad"))
                .map(s -> new ActiveServiceEffectDTO(
                        s.getServices().getName(),
                        s.getServices().getUpgradePerPurchase(),
                        s.getCurrentUpdate()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(effects);
    }




}
