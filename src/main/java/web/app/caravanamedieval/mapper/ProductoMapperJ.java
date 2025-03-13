package web.app.caravanamedieval.mapper;

import web.app.caravanamedieval.dto.ProductoDTOJ;
import web.app.caravanamedieval.model.Producto;

public class ProductoMapperJ {

    public static ProductoDTOJ toDTOJ(Producto producto) {
        if (producto == null) return null;

        ProductoDTOJ productoDTO = new ProductoDTOJ();
        productoDTO.setIdProducto(producto.getIdProducto());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPeso(producto.getPeso());

        return productoDTO;
    }

    public static Producto toEntity(ProductoDTOJ dto) {
        if (dto == null) return null;

        Producto producto = new Producto();
        producto.setIdProducto(dto.getIdProducto());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPeso(dto.getPeso());

        return producto;
    }
}
