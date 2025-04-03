package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.GameDTO;
import web.app.caravanamedieval.model.Caravan;
import web.app.caravanamedieval.model.Map;
import web.app.caravanamedieval.model.Game;
@Mapper(componentModel = "spring")
public interface GameMapper {

    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    @Mapping(source = "caravan.idCaravan", target = "caravanId")
    @Mapping(source = "map.idMap", target = "mapId")
    GameDTO toDTO(Game game);

    @Mapping(target = "idGame", ignore = true)
    @Mapping(target = "caravan", ignore = true)
    @Mapping(target = "map", ignore = true)
    Game toEntity(GameDTO gameDTO);

    @Mapping(target = "idGame", ignore = true)
    @Mapping(target = "caravan", ignore = true)
    @Mapping(target = "map", ignore = true)
    void updateEntity(@MappingTarget Game game, GameDTO gameDTO);




}


