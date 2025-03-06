package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.ProductoDTO;
import web.app.caravanamedieval.model.Producto;

@Mapper
public interface ProductoMapper {
    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    ProductoDTO toDTO(Producto producto);

    //Ignora el id
    @Mapping(target = "idProducto", ignore = true)
    Producto toEntity(ProductoDTO dto);
}
