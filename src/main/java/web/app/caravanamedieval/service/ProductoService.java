package web.app.caravanamedieval.service;
import web.app.caravanamedieval.model.*;
import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.model.Producto;
import java.util.List;

public interface ProductoService {
    public Producto crearProducto(ProductoDTO producto);
    public Producto getProducto(Integer id);
    List<Producto> listarTodos();
    public Producto getProductoByNombre(String nombre);
    public Producto actualizarProducto(Integer id, ProductoDTO actualizado);
//    public Producto actualizarProductoEntero(Integer id , Producto actualizado);
    public void eliminarProducto(Integer id);
    //BORRAR EL DE ABAJO
    public List<ProductoDTOJ> recuperarProductos();


}
