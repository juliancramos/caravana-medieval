package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.ProductsByCaravanDTO;
import web.app.caravanamedieval.model.ProductsByCaravan;

@Mapper(componentModel = "spring")
public interface ProductsByCaravanMapper {
    ProductsByCaravanMapper INSTANCE = Mappers.getMapper(ProductsByCaravanMapper.class);

    @Mapping(source = "caravan.idCaravan", target = "idCaravan")
    @Mapping(source = "product.idProduct", target = "idProduct")
    @Mapping(source = "quantity", target = "quantity")
    ProductsByCaravanDTO toDTO(ProductsByCaravan productsByCaravan);

    @Mapping(target = "caravan.idCaravan", source = "idCaravan")
    @Mapping(target = "product.idProduct", source = "idProduct")
    @Mapping(target = "quantity", source = "quantity")
    ProductsByCaravan toEntity(ProductsByCaravanDTO dto);
}


