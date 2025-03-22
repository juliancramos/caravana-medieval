package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.MapaDTO;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.model.Partida;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CiudadMapper.class})
public interface MapaMapper {

    Mapa toEntity(MapaDTO dto);
    MapaDTO toDTO(Mapa entity);

    @Mapping(target = "idMapa", ignore = true)
    @Mapping(target = "ciudades", ignore = true)
    void updateEntity(@MappingTarget Mapa entity, MapaDTO dto);
}







