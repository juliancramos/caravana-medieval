package web.app.caravanamedieval.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.BuyProductDTO;
import web.app.caravanamedieval.model.*;
import web.app.caravanamedieval.repository.*;

@Service
public class StoreService {

    private final CaravanRepository caravanRepository;
    private final ProductRepository productRepository;
    private final ProductsByCaravanRepository caravanProductRepository;
    private final ProductsByCityRepository cityProductRepository;

    public StoreService(CaravanRepository caravanRepository, ProductRepository productRepository,
            ProductsByCaravanRepository caravanProductRepository, ProductsByCityRepository cityProductRepository) {
        this.caravanRepository = caravanRepository;
        this.productRepository = productRepository;
        this.caravanProductRepository = caravanProductRepository;
        this.cityProductRepository = cityProductRepository;
    }

    @Transactional
    public void buyProduct(BuyProductDTO dto) {
        Caravan caravan = getCaravan(dto.getCaravanId());
        Product product = getProduct(dto.getProductId());
        ProductsByCity cityProduct = getCityProduct(dto.getCityId(), dto.getProductId());

        long totalPrice = calculateTotalPrice(cityProduct, dto.getQuantity());

        //Revisar que la caravana tenga suficiente dinero
        validateGold(caravan, totalPrice);

        updateCaravanInventory(caravan, product, dto.getQuantity());
        updateCityInventory(cityProduct, dto.getQuantity());
        deductGold(caravan, totalPrice);
    }

    private Caravan getCaravan(Long id) {
        return caravanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caravan not found"));
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    private ProductsByCity getCityProduct(Long cityId, Long productId) {
        return cityProductRepository.findById_CityIdAndId_ProductIdOrderById_ProductId(cityId, productId)
                .orElseThrow(() -> new RuntimeException("Product not available in this city"));
    }

    private long calculateTotalPrice(ProductsByCity cityProduct, int quantity) {
        double stock = cityProduct.getQuantity();
        double unitPrice = cityProduct.getSupplyFactor() / (1 + stock);
        return Math.round(unitPrice * quantity);
    }

    private void validateGold(Caravan caravan, long totalPrice) {
        if (caravan.getAvailableMoney() < totalPrice) {
            throw new RuntimeException("Not enough gold to complete the purchase");
        }
    }

    private void updateCaravanInventory(Caravan caravan, Product product, int quantity) {
        ProductsByCaravan caravanProduct = caravanProductRepository
                .findById_CaravanIdAndId_ProductIdOrderById_ProductId(caravan.getIdCaravan(), product.getIdProduct())
                .orElse(new ProductsByCaravan(caravan, product, 0));

        caravanProduct.setQuantity(caravanProduct.getQuantity() + quantity);
        caravanProductRepository.save(caravanProduct);
    }

    private void updateCityInventory(ProductsByCity cityProduct, int quantity) {
        int newQuantity = cityProduct.getQuantity() - quantity;
        if (newQuantity <= 0) {
            cityProductRepository.delete(cityProduct);
        } else {
            cityProduct.setQuantity(newQuantity);
            cityProductRepository.save(cityProduct);
        }
    }

    private void deductGold(Caravan caravan, long totalPrice) {
        caravan.setAvailableMoney(caravan.getAvailableMoney() - totalPrice);
        caravanRepository.save(caravan);
    }
}
