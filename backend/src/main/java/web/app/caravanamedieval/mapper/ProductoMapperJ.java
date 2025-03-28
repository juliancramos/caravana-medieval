package web.app.caravanamedieval.mapper;

import web.app.caravanamedieval.dto.ProductoDTOJ;
import web.app.caravanamedieval.model.Product;

public class ProductoMapperJ {

    public static ProductoDTOJ toDTOJ(Product product) {
        if (product == null) return null;

        ProductoDTOJ productoDTO = new ProductoDTOJ();
        productoDTO.setIdProducto(product.getIdProduct());
        productoDTO.setNombre(product.getName());
        productoDTO.setDescripcion(product.getDescription());
        productoDTO.setPeso(product.getWeight());

        return productoDTO;
    }

    public static Product toEntity(ProductoDTOJ dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setIdProduct(dto.getIdProducto());
        product.setName(dto.getNombre());
        product.setDescription(dto.getDescripcion());
        product.setWeight(dto.getPeso());

        return product;
    }
}
