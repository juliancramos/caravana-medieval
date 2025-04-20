package web.app.caravanamedieval.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.BuyProductDTO;
import web.app.caravanamedieval.dto.SellProductDTO;
import web.app.caravanamedieval.model.*;
import web.app.caravanamedieval.model.keys.ProductsByCityKey;
import web.app.caravanamedieval.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    @Transactional
    public void sellProduct(BuyProductDTO dto) {
        Caravan caravan = getCaravan(dto.getCaravanId());
        Product product = getProduct(dto.getProductId());

        // validar existencia del producto en la caravana
        ProductsByCaravan caravanProduct = caravanProductRepository
                .findById_CaravanIdAndId_ProductIdOrderById_ProductId(dto.getCaravanId(), dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found in caravan"));

        //validar si hay suficiente cantidad
        if (caravanProduct.getQuantity() < dto.getQuantity()) {
            throw new RuntimeException("Not enough product quantity to sell");
        }

        // Obtener o crear producto en ciudad
        ProductsByCity cityProduct = cityProductRepository
                .findById_CityIdAndId_ProductIdOrderById_ProductId(dto.getCityId(), dto.getProductId())
                .orElse(null);

        long unitPrice;
        double stock;

        if (cityProduct != null) {
            stock = cityProduct.getQuantity();
            unitPrice = Math.round((double) cityProduct.getDemandFactor() / (1 + stock));
        } else {
            stock = 0;
            unitPrice = Math.round((double) 100L / (1 + stock)); // Defaults
        }

        long totalPrice = unitPrice * dto.getQuantity();

        // Actualizar inventario caravana
        int remaining = caravanProduct.getQuantity() - dto.getQuantity();
        if (remaining <= 0) {
            //por ahora se borra pero puede que cambie luego
            caravanProductRepository.delete(caravanProduct);
        } else {
            caravanProduct.setQuantity(remaining);
            caravanProductRepository.save(caravanProduct);
        }

        // Si no existía la relación con ciudad, crearla
        if (cityProduct == null) {
            cityProduct = new ProductsByCity();
            ProductsByCityKey key = new ProductsByCityKey(dto.getCityId(), dto.getProductId());
            cityProduct.setId(key);
            cityProduct.setCity(caravan.getCurrentCity());
            cityProduct.setProduct(product);
            cityProduct.setSupplyFactor(100L);
            cityProduct.setDemandFactor(100L);
            cityProduct.setQuantity(dto.getQuantity());
        } else {
            cityProduct.setQuantity(cityProduct.getQuantity() + dto.getQuantity());
        }

        cityProductRepository.save(cityProduct);

        // Actualizar oro de la caravana
        caravan.setAvailableMoney(caravan.getAvailableMoney() + totalPrice);
        caravanRepository.save(caravan);
    }



    public List<SellProductDTO> getProductsForSale(Long caravanId, Long cityId) {
        List<ProductsByCaravan> caravanProducts = caravanProductRepository
                .findByCaravan_IdCaravan(caravanId);

        return caravanProducts.stream().map(p -> {
            Optional<ProductsByCity> cityProductOpt = cityProductRepository
                    .findById_CityIdAndId_ProductId(cityId, p.getProduct().getIdProduct());

            long price = cityProductOpt
                    .map(cityProduct -> Math.round((double) cityProduct.getDemandFactor() / (1 + cityProduct.getQuantity())))
                    .orElse(100L); // default si no hay en ciudad

            return new SellProductDTO(p.getProduct(), p.getQuantity(), price);
        }).collect(Collectors.toList());
    }




    //Complementarios
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
