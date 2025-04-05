package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.ProductsByCaravanDTO;
import web.app.caravanamedieval.model.ProductsByCaravan;

@Mapper(componentModel = "spring")
public interface ProductsByCaravanMapper {
    ProductsByCaravanMapper INSTANCE = Mappers.getMapper(ProductsByCaravanMapper.class);

    @Mapping(source = "caravan.idCaravan", target = "idCaravan")
    @Mapping(source = "product.idProduct", target = "idProduct")
    ProductsByCaravanDTO toDTO(ProductsByCaravan productsByCaravan);

    @Mapping(target = "id.caravanId", source = "idCaravan")
    @Mapping(target = "id.productId", source = "idProduct")
    @Mapping(target = "caravan", ignore = true)
    @Mapping(target = "product", ignore = true)
    ProductsByCaravan toEntity(ProductsByCaravanDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caravan", ignore = true)
    @Mapping(target ="product", ignore = true)
    void updateEntity(@MappingTarget ProductsByCaravan entity, ProductsByCaravanDTO dto);
}


