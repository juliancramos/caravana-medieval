package web.app.caravanamedieval.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.RouteDTO;
import web.app.caravanamedieval.model.Route;

@Mapper(componentModel = "spring", uses = {CityMapper.class})
public interface RouteMapper {

    RouteMapper INSTANCE = Mappers.getMapper(RouteMapper.class);

    @Mapping(target = "idRoute", ignore = true)
    @Mapping(target = "originCity", ignore = true)
    @Mapping(target = "destinationCity", ignore = true)
    Route toEntity(RouteDTO dto);

    @Mapping(source = "originCity.idCity", target = "originCityId")
    @Mapping(source = "destinationCity.idCity", target = "destinationCityId")
    RouteDTO toDTO(Route route);

    @Mapping(target = "idRoute", ignore = true)
    @Mapping(target = "originCity", ignore = true)
    @Mapping(target = "destinationCity", ignore = true)
    void updateEntity(@MappingTarget Route entity, RouteDTO dto);
}
