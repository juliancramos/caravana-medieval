package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.ProductosXCaravanaDTO;
import web.app.caravanamedieval.model.ProductosXCaravana;

@Mapper(componentModel = "spring")
public interface ProductosXCaravanaMapper {
    ProductosXCaravanaMapper INSTANCE = Mappers.getMapper(ProductosXCaravanaMapper.class);

    // Convertir de entidad a DTO
    @Mapping(source = "caravana.idCaravana", target = "idCaravana")
    @Mapping(source = "producto.idProducto", target = "idProducto")
    @Mapping(source = "cantidad", target = "cantidad")
    ProductosXCaravanaDTO toDTO(ProductosXCaravana productosXCaravana);

    // Convertir de DTO a entidad
    @Mapping(target = "caravana.idCaravana", source = "idCaravana")
    @Mapping(target = "producto.idProducto", source = "idProducto")
    @Mapping(target = "cantidad", source = "cantidad")
    ProductosXCaravana toEntity(ProductosXCaravanaDTO dto);
}

