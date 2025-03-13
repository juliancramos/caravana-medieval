package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.CiudadDTO;
import web.app.caravanamedieval.model.Ciudad;

@Mapper(componentModel = "spring")
public interface CiudadMapper {

    CiudadMapper INSTANCE = Mappers.getMapper(CiudadMapper.class);

    @Mapping(target = "mapaId", source = "mapa.idMapa")
    CiudadDTO toDTO(Ciudad ciudad);

    @Mapping(target = "idCiudad", ignore = true)
    Ciudad toEntity(CiudadDTO ciudadDTO);
}
