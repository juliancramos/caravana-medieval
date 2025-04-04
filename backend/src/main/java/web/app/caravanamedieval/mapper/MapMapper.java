package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.MapDTO;
import web.app.caravanamedieval.model.Map;

@Mapper(componentModel = "spring", uses = {CityMapper.class, GameMapper.class})
public interface MapMapper {

    MapMapper INSTANCE = Mappers.getMapper(MapMapper.class);

    @Mapping(target = "idMap", ignore = true)
    Map toEntity(MapDTO dto);
    MapDTO toDTO(Map entity);

    @Mapping(target = "idMap", ignore = true)
    @Mapping(target = "cities", ignore = true)
    @Mapping(target = "games", ignore = true)
    void updateEntity(@MappingTarget Map entity, MapDTO dto);
}





