package web.app.caravanamedieval.service;

import web.app.caravanamedieval.model.Producto;
import java.util.List;

public interface ProductoService {
    public Producto crearProducto(Producto producto);
    public Producto getProducto(Integer id);
    List<Producto> listarTodos();
    public Producto getProductoByNombre(String nombre);
    public Producto actualizarProducto(Integer id, Producto actualizado);
//    public Producto actualizarProductoEntero(Integer id , Producto actualizado);
    public void eliminarProducto(Integer id);


}
