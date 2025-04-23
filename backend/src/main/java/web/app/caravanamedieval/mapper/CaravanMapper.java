package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.CaravanDTO;
import web.app.caravanamedieval.model.Caravan;

@Mapper(componentModel = "spring")
public interface CaravanMapper {
    CaravanMapper INSTANCE = Mappers.getMapper(CaravanMapper.class);

    @Mapping(target = "idCaravan", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "games", ignore = true)
    @Mapping(target = "currentCity", ignore = true)
    Caravan toEntity(CaravanDTO caravanDTO);

    CaravanDTO toDTO(Caravan caravan);

    @Mapping(target = "idCaravan", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "games", ignore = true)
    @Mapping(target = "currentCity", ignore = true) // Igual que arriba
    void updateEntity(@MappingTarget Caravan entity, CaravanDTO dto);
}


