package web.app.caravanamedieval.service;

import web.app.caravanamedieval.model.Producto;
import java.util.List;

public interface ProductoService {
    public Producto crearProducto(Producto producto);
    public Producto getProducto(Integer id);
    List<Producto> listarTodos();

}
