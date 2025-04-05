package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.PlayerDTO;
import web.app.caravanamedieval.model.Player;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    PlayerDTO toDTO(Player player);

    @Mapping(target = "idPlayer", ignore = true)
    Player toEntity(PlayerDTO dto);

    @Mapping(target = "idPlayer", ignore = true)
    void updateEntity(@MappingTarget Player player, PlayerDTO dto);
}
