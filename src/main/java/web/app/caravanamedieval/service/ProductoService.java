package web.app.caravanamedieval.service;

import web.app.caravanamedieval.model.Producto;

public interface ProductoService {
    public Producto crearProducto(Producto producto);
    public Producto getProducto(Long id);
}
