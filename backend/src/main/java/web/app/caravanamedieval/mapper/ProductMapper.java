package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.ProductDTO;
import web.app.caravanamedieval.model.Product;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDTO(Product product);

    //Ignora el id
    @Mapping(target = "idProduct", ignore = true)
    Product toEntity(ProductDTO dto);
}
