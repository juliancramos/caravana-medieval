package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.RutaDTO;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Ruta;
@Mapper(componentModel = "spring")
public interface RutaMapper {

    RutaMapper INSTANCE = Mappers.getMapper(RutaMapper.class);

    @Mapping(target = "idRuta", ignore = true) // Se ignora el ID porque lo genera la DB
    @Mapping(target = "ciudadOrigen", source = "ciudadOrigen")
    @Mapping(target = "ciudadDestino", source = "ciudadDestino")
    Ruta toEntity(RutaDTO dto, Ciudad ciudadOrigen, Ciudad ciudadDestino);

    @Mapping(target = "ciudadOrigenId", source = "ciudadOrigen.idCiudad")
    @Mapping(target = "ciudadDestinoId", source = "ciudadDestino.idCiudad")
    RutaDTO toDTO(Ruta ruta);
}
