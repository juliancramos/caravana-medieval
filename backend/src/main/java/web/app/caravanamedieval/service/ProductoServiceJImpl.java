package web.app.caravanamedieval.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.ProductoDTOJ;
import web.app.caravanamedieval.mapper.ProductoMapperJ;
import web.app.caravanamedieval.model.Product;
import web.app.caravanamedieval.repository.ProductRepository;
import web.app.caravanamedieval.repository.ProductsByCaravanRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceJImpl implements ProductoServiceJ {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductsByCaravanRepository productsByCaravanRepository;

    @Override
    public List<ProductoDTOJ> listarTodos() {
        return productRepository.findAll().stream()
                .map(ProductoMapperJ::toDTOJ)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductoDTOJ> buscarProducto(Long id) {
        return productRepository.findById(id)
                .map(ProductoMapperJ::toDTOJ);
    }

    @Override
    public Product guardarProducto(ProductoDTOJ productoDTO) {
        Product product = ProductoMapperJ.toEntity(productoDTO);
      return productRepository.save(product);
    }

    @Transactional
    @Override
    public void borrarProducto(Long id) {
        // Primero elimina relaciones en tabla intermedia
        productsByCaravanRepository.deleteByProduct_IdProduct(id);
        //Luego elimina el producto
        productRepository.deleteById(id);
    }
}
