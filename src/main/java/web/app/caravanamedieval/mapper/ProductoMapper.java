package web.app.caravanamedieval.mapper;

import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.model.*;

public class ProductoMapper {

    public static ProductoDTO toDTO(Producto producto) {
        if (producto == null) return null;

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setIdProducto(producto.getIdProducto()); 
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPeso(producto.getPeso());

        return productoDTO;
    }

    public static Producto toEntity(ProductoDTO dto) { 
        if (dto == null) return null;

        Producto producto = new Producto(); 

        producto.setIdProducto(dto.getIdProducto()); 
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPeso(dto.getPeso());

        return producto;
    }
}
