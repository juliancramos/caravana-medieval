package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.app.caravanamedieval.model.Service;
import web.app.caravanamedieval.service.ServicesService;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServicesService servicesService;


    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        List<Service> services = servicesService.getServices();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Integer id) {
        Service service = servicesService.findServiceById(id);
        return service != null ? new ResponseEntity<>(service, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/services")
    public ResponseEntity<?> getServices() {
        return ResponseEntity.ok(servicesService.getServices());
    }

    
    @PostMapping("/create")
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        Service newService = servicesService.createService(service);
        return new ResponseEntity<>(newService, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Integer id, @RequestBody Service service) {
        service.setIdService(id);
        Service updatedService = servicesService.updateService(service);
        return updatedService != null ? new ResponseEntity<>(updatedService, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Integer id) {
        boolean isDeleted = servicesService.deleteService(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
