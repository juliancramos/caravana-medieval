package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.PlayerDTO;
import web.app.caravanamedieval.model.Player;

@Mapper
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    PlayerDTO toDTO(Player player);

    @Mapping(target = "idPlayer", ignore = true)
    Player toEntity(PlayerDTO dto);
}
