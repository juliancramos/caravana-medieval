package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import web.app.caravanamedieval.dto.CityDTO;
import web.app.caravanamedieval.model.City;

@Mapper(componentModel = "spring", uses = {RouteMapper.class})
public interface CityMapper {

    City toEntity(CityDTO dto);

    CityDTO toDTO(City entity);

    @Mapping(target = "idCity", ignore = true)
    @Mapping(target = "originRoutes", ignore = true)
    @Mapping(target = "destinationRoutes", ignore = true)
    @Mapping(target = "map", ignore = true)
    void updateEntity(@MappingTarget City entity, CityDTO dto);
}

