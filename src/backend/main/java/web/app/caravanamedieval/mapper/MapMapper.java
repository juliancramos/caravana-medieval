package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import web.app.caravanamedieval.dto.MapDTO;
import web.app.caravanamedieval.model.Map;

@Mapper(componentModel = "spring", uses = {CityMapper.class})
public interface MapMapper {

    Map toEntity(MapDTO dto);
    MapDTO toDTO(Map entity);

    @Mapping(target = "idMap", ignore = true)
    @Mapping(target = "cities", ignore = true)
    void updateEntity(@MappingTarget Map entity, MapDTO dto);
}







