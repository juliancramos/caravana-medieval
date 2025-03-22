package web.app.caravanamedieval.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import web.app.caravanamedieval.dto.RutaDTO;
import web.app.caravanamedieval.model.Ruta;
@Mapper(componentModel = "spring")
public interface RutaMapper {

    @Mapping(target = "idRuta", ignore = true)
    @Mapping(target = "ciudadOrigen", expression = "java(context.getCiudadOrigen())")
    @Mapping(target = "ciudadDestino", expression = "java(context.getCiudadDestino())")
    Ruta toEntity(RutaDTO dto, @Context RutaContext context);

    @Mapping(target = "ciudadOrigenId", source = "ciudadOrigen.idCiudad")
    @Mapping(target = "ciudadDestinoId", source = "ciudadDestino.idCiudad")
    RutaDTO toDTO(Ruta ruta);
}



