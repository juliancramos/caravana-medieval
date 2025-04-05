package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.ProductsByCityDTO;
import web.app.caravanamedieval.model.ProductsByCity;
import web.app.caravanamedieval.service.ProductsByCityServiceImpl;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products-by-city")
public class ProductsByCityController {
    private final ProductsByCityServiceImpl productsByCityService;

    @Autowired
    public ProductsByCityController(ProductsByCityServiceImpl productsByCityService) {
        this.productsByCityService = productsByCityService;
    }

    @PostMapping("/assign")
    public ResponseEntity<ProductsByCity> assignProductToCity(@RequestBody ProductsByCityDTO dto) {
        ProductsByCity assignment = productsByCityService.assignProductToCity(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{productId}/{cityId}")
                .buildAndExpand(dto.getProductId(), dto.getCityId())
                .toUri();

        return ResponseEntity.created(location).body(assignment);
    }

    @DeleteMapping("/remove/products/{productId}/cities/{cityId}")
    public ResponseEntity<Void> removeAssignment(@PathVariable Long productId, @PathVariable Long cityId) {
        productsByCityService.removeAssignment(productId, cityId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{productId}/city/{cityId}")
    public ResponseEntity<ProductsByCity> getProduct(@PathVariable Long productId, @PathVariable Long cityId) {
        return ResponseEntity.ok(productsByCityService.getProduct(productId, cityId));
    }

    @GetMapping("/products/city/{id}")
    public ResponseEntity<List<ProductsByCity>> getProductsByCityId(@PathVariable Long id) {
        return ResponseEntity.ok(productsByCityService.getProductsByCityId(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductsByCity>> listProductsByCity() {
        return ResponseEntity.ok(productsByCityService.listProductsByCity());
    }

    @PutMapping("/update")
    public ResponseEntity<ProductsByCity> updateAssignment(@RequestBody ProductsByCityDTO dto) {
        ProductsByCity updatedAssignment = productsByCityService.updateAssignment(dto);
        return ResponseEntity.ok(updatedAssignment);
    }
}

