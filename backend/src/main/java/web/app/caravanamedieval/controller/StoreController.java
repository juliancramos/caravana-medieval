package web.app.caravanamedieval.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.app.caravanamedieval.dto.BuyProductDTO;
import web.app.caravanamedieval.dto.SellProductDTO;
import web.app.caravanamedieval.service.StoreService;

import java.util.List;

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

    @PostMapping("/sell")
    public ResponseEntity<Void> sellProduct(@RequestBody BuyProductDTO dto) {
        storeService.sellProduct(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products-for-sale/caravan/{caravanId}/city/{cityId}")
    public ResponseEntity<List<SellProductDTO>> getProductsForSale(@PathVariable Long caravanId, @PathVariable Long cityId) {
        return ResponseEntity.ok(storeService.getProductsForSale(caravanId, cityId));
    }


}
