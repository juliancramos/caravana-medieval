package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.JugadorDTO;
import web.app.caravanamedieval.model.Jugador;

@Mapper
public interface JugadorMapper {
    JugadorMapper INSTANCE = Mappers.getMapper(JugadorMapper.class);

    JugadorDTO toDTO(Jugador jugador);

    // Ignora el id en la conversión
    @Mapping(target = "idJugador", ignore = true)
    Jugador toEntity(JugadorDTO dto);
}
