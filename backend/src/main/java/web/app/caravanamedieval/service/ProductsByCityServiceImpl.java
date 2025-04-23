package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.ProductsByCityDTO;
import web.app.caravanamedieval.mapper.ProductsByCityMapper;
import web.app.caravanamedieval.model.City;
import web.app.caravanamedieval.model.Product;
import web.app.caravanamedieval.model.ProductsByCity;
import web.app.caravanamedieval.model.keys.ProductsByCityKey;
import web.app.caravanamedieval.repository.CityRepository;
import web.app.caravanamedieval.repository.ProductRepository;
import web.app.caravanamedieval.repository.ProductsByCityRepository;

import java.util.List;

@Service
public class ProductsByCityServiceImpl {
    private final ProductsByCityRepository productsByCityRepository;
    private final CityRepository cityRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductsByCityServiceImpl(ProductsByCityRepository productsByCityRepository, CityRepository cityRepository,
                                     ProductRepository productRepository) {
        this.productsByCityRepository = productsByCityRepository;
        this.cityRepository = cityRepository;
        this.productRepository = productRepository;
    }

    public ProductsByCity assignProductToCity(ProductsByCityDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found"));

        ProductsByCityKey key = new ProductsByCityKey(dto.getCityId(), dto.getProductId());

        if (productsByCityRepository.existsById(key)) {
            throw new IllegalArgumentException("Assignment already exists");
        }

        ProductsByCity assignment = ProductsByCityMapper.INSTANCE.toEntity(dto);
        assignment.setProduct(product);
        assignment.setCity(city);

        return productsByCityRepository.save(assignment);
    }

    public void removeAssignment(Long productId, Long cityId) {
        ProductsByCityKey key = new ProductsByCityKey(cityId, productId);
        if (!productsByCityRepository.existsById(key)) {
            throw new EntityNotFoundException("Assignment not found");
        }
        productsByCityRepository.deleteById(key);
    }

    public ProductsByCity getProduct(Long productId, Long cityId) {
        ProductsByCityKey key = new ProductsByCityKey(cityId, productId);
        return productsByCityRepository.findById(key)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
    }

    public List<ProductsByCity> getProductsByCityId(Long cityId) {
        return productsByCityRepository.findByCity_IdCity(cityId);
    }

    public List<ProductsByCity> listProductsByCity() {
        return productsByCityRepository.findAll();
    }

    @Transactional
    public ProductsByCity updateAssignment(ProductsByCityDTO dto) {
        ProductsByCityKey key = new ProductsByCityKey(dto.getCityId(), dto.getProductId());

        ProductsByCity assignment = productsByCityRepository.findById(key)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        ProductsByCityMapper.INSTANCE.updateEntity(assignment, dto);

        return productsByCityRepository.save(assignment);
    }
}
