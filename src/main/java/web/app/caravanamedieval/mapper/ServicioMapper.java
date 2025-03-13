package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.ServicioDTO;
import web.app.caravanamedieval.model.Servicio;

@Mapper
public interface ServicioMapper {
    ServicioMapper INSTANCE = Mappers.getMapper(ServicioMapper.class);

    ServicioDTO toDTO(Servicio servicio);

    // Ignora el id en la conversión
    @Mapping(target = "idServicio", ignore = true)
    Servicio toEntity(ServicioDTO dto);
}

