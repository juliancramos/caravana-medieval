package web.app.caravanamedieval.service;
import web.app.caravanamedieval.model.*;
import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.model.Producto;
import java.util.List;

public interface ProductoService {
    public Producto crearProducto(ProductoDTO producto);
    public Producto getProducto(Long id);
    List<Producto> listarTodos();
    public Producto getProductoByNombre(String nombre);
    public Producto actualizarProducto(Long id, ProductoDTO actualizado);
    public void eliminarProducto(Long id);
    //Model and view
    public List<ProductoDTOJ> recuperarProductos();


}
