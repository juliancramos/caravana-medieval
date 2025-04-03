package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.model.ProductsByCaravan;
import web.app.caravanamedieval.service.*;
import java.net.URI;
import java.util.List;


@Controller
@RequestMapping("/products-by-caravan")
public class ProductsByCaravanController {
    private final ProductsByCaravanServiceImpl productsByCaravanService;
    @Autowired
    public ProductsByCaravanController(ProductsByCaravanServiceImpl productsByCaravanService) {
        this.productsByCaravanService = productsByCaravanService;
    }

    @PostMapping("/assign")
    public ResponseEntity<ProductsByCaravan> assignProductsToCaravans(@RequestBody ProductsByCaravanDTO productsByCaravanDTO) {
        ProductsByCaravan assignment = productsByCaravanService.assignProductToCaravan(productsByCaravanDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idProduct}/{idCaravan}")
                .buildAndExpand(productsByCaravanDTO.getIdProduct(), productsByCaravanDTO.getIdCaravan())
                .toUri();
        return ResponseEntity.created(location).body(assignment);
    }

    @DeleteMapping("/remove/products/{idProduct}/caravans/{idCaravan}")
    public ResponseEntity<Void> removeAssignment (@PathVariable Long idProduct, @PathVariable Long idCaravan){
        productsByCaravanService.removeAssignment(idProduct, idCaravan);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductsByCaravan>> listProductsByCaravan(){
        return ResponseEntity.ok(productsByCaravanService.getProductsByCaravan());
    }

}