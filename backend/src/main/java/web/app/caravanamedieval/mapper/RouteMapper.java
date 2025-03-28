package web.app.caravanamedieval.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import web.app.caravanamedieval.dto.RouteDTO;
import web.app.caravanamedieval.model.Route;
@Mapper(componentModel = "spring")
public interface RouteMapper {

    @Mapping(target = "idRoute", ignore = true)
    @Mapping(target = "originCity", expression = "java(context.getOriginCity())")
    @Mapping(target = "destinationCity", expression = "java(context.getDestinationCity())")
    Route toEntity(RouteDTO dto, @Context RouteContext context);

    @Mapping(target = "originCityId", source = "originCity.idCity")
    @Mapping(target = "destinationCityId", source = "destinationCity.idCity")
    RouteDTO toDTO(Route route);
}




