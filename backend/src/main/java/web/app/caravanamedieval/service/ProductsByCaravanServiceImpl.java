package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.ProductsByCaravanDTO;
import web.app.caravanamedieval.model.Caravan;
import web.app.caravanamedieval.model.Product;
import web.app.caravanamedieval.model.ProductsByCaravan;
import web.app.caravanamedieval.model.ProductsByCaravanKey;
import web.app.caravanamedieval.repository.CaravanRepository;
import web.app.caravanamedieval.repository.ProductRepository;
import web.app.caravanamedieval.repository.ProductsByCaravanRepository;

import java.util.List;

@Service
public class ProductsByCaravanServiceImpl {
    private final ProductsByCaravanRepository productsByCaravanRepository;
    private final CaravanRepository caravanRepository;
    private final ProductRepository productRepository;
    @Autowired
    public ProductsByCaravanServiceImpl(ProductsByCaravanRepository productsByCaravanRepository,
                                        CaravanRepository caravanRepository, ProductRepository productRepository) {
        this.productsByCaravanRepository = productsByCaravanRepository;
        this.caravanRepository = caravanRepository;
        this.productRepository = productRepository;
    }

    public ProductsByCaravan assignProductToCaravan(ProductsByCaravanDTO productsByCaravanDTO) {
        Product product = productRepository.findById(productsByCaravanDTO.getIdProduct())
                .orElseThrow( ()-> new RuntimeException("Product not found"));
        Caravan caravan = caravanRepository.findById(productsByCaravanDTO.getIdCaravan())
                .orElseThrow( ()-> new RuntimeException("Caravan not found"));

        ProductsByCaravanKey productsByCaravanKey= new ProductsByCaravanKey();
        productsByCaravanKey.setCaravanId(productsByCaravanDTO.getIdCaravan());
        productsByCaravanKey.setProductId(productsByCaravanDTO.getIdProduct());

        if(productsByCaravanRepository.existsById(productsByCaravanKey)){
            throw new IllegalArgumentException("Assignment already exists");
        }

        ProductsByCaravan assignment = new ProductsByCaravan();
        assignment.setId(productsByCaravanKey);
        assignment.setProduct(product);
        assignment.setCaravan(caravan);
        assignment.setQuantity(productsByCaravanDTO.getQuantity());

        return productsByCaravanRepository.save(assignment);
    }

    public void removeAssignment (Long productId, Long caravanId){
        ProductsByCaravanKey key = new ProductsByCaravanKey(caravanId, productId);
        if (! productsByCaravanRepository.existsById(key)){
            throw new EntityNotFoundException("Assignment not found");
        }
        productsByCaravanRepository.deleteById(key);
    }


    public List<ProductsByCaravan> getProductsByCaravan() {
        return  productsByCaravanRepository.findAll();
    }
}
