package web.app.caravanamedieval.service;
import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.model.Product;
import java.util.List;

public interface ProductService {
    public Product createProduct(ProductDTO producto);
    public Product getProduct(Long id);
    List<Product> getProducts();
    public Product getProductByName(String nombre);
    public Product updateProduct(Long id, ProductDTO actualizado);
    public void deleteProduct(Long id);
    //Model and view
    public List<ProductoDTOJ> recuperarProductos();


}
