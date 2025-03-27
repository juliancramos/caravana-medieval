package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.ProductoDTOJ;
import web.app.caravanamedieval.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductoServiceJ {
    List<ProductoDTOJ> listarTodos();
    Optional<ProductoDTOJ> buscarProducto(Long id);
    Product guardarProducto(ProductoDTOJ productoDTO);
    void borrarProducto(Long id);
}
