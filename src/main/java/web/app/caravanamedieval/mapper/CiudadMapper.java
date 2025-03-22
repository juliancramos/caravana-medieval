package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.CiudadDTO;
import web.app.caravanamedieval.model.Ciudad;

@Mapper(componentModel = "spring", uses = {RutaMapper.class})
public interface CiudadMapper {

    Ciudad toEntity(CiudadDTO dto);

    CiudadDTO toDTO(Ciudad entity);

    @Mapping(target = "idCiudad", ignore = true)
    @Mapping(target = "rutasOrigen", ignore = true)
    @Mapping(target = "rutasDestino", ignore = true)
    @Mapping(target = "mapa", ignore = true)
    void updateEntity(@MappingTarget Ciudad entity, CiudadDTO dto);
}

