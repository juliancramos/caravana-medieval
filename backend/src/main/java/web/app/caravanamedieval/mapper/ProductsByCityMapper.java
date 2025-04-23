package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.ProductsByCityDTO;
import web.app.caravanamedieval.model.ProductsByCity;

@Mapper(componentModel = "spring")
public interface ProductsByCityMapper {
    ProductsByCityMapper INSTANCE = Mappers.getMapper(ProductsByCityMapper.class);

    @Mapping(source = "city.idCity", target = "cityId")
    @Mapping(source = "product.idProduct", target = "productId")
    ProductsByCityDTO toDTO(ProductsByCity productsByCity);

    @Mapping(target = "id.cityId", source = "cityId")
    @Mapping(target = "id.productId", source = "productId")
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "product", ignore = true)
    ProductsByCity toEntity(ProductsByCityDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "product", ignore = true)
    void updateEntity(@MappingTarget ProductsByCity entity, ProductsByCityDTO dto);
}
