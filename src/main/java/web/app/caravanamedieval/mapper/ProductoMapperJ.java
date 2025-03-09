package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.ProductoDTOJ;
import web.app.caravanamedieval.model.Producto;

@Mapper
public interface ProductoMapperJ {
    ProductoMapperJ INSTANCE = Mappers.getMapper(ProductoMapperJ.class);

    ProductoDTOJ toDTOJ(Producto producto);

    //Ignora el id
    @Mapping(target = "idProducto", ignore = true)
    Producto toEntity(ProductoDTOJ dto);
}
