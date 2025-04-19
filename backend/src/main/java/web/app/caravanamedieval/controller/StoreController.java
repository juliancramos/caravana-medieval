package web.app.caravanamedieval.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.app.caravanamedieval.dto.BuyProductDTO;
import web.app.caravanamedieval.service.StoreService;

@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyProduct(@RequestBody BuyProductDTO dto) {
        storeService.buyProduct(dto);
        return ResponseEntity.ok().build();
    }
}
