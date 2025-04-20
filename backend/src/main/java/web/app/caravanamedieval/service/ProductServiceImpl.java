package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.mapper.*;
import web.app.caravanamedieval.model.Product;
import web.app.caravanamedieval.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Product product = ProductMapper.INSTANCE.toEntity(productDTO);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto con ID " + id + " no encontrado"));
    }

    @Override
    public Product getProductByName(String nombre) {
        return productRepository.findByName(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, ProductDTO actualizado) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto con ID " + id + " no encontrado"));

        if (actualizado.getName() != null) {
            product.setName(actualizado.getName());
        }
        if (actualizado.getDescription() != null) {
            product.setDescription(actualizado.getDescription());
        }
        if (actualizado.getWeight() != null) {
            product.setWeight(actualizado.getWeight());
        }
        if(actualizado.getImgUrl() != null){
            product.setImgUrl(actualizado.getImgUrl());
        }

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Producto con ID " + id + " no encontrado.");
        }
        productRepository.deleteById(id);
    }
}
