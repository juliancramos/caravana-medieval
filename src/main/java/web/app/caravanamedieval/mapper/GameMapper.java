package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import web.app.caravanamedieval.dto.GameDTO;
import web.app.caravanamedieval.model.Caravan;
import web.app.caravanamedieval.model.Map;
import web.app.caravanamedieval.model.Game;
@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(source = "caravan.idCaravan", target = "caravanId")
    @Mapping(source = "map.idMap", target = "mapId")
    GameDTO toDTO(Game game);

    @Mapping(source = "caravanId", target = "caravan")
    @Mapping(source = "mapId", target = "map")
    Game toEntity(GameDTO gameDTO);

    default Caravan map(Long id) {
        if (id == null) {
            return null;
        }
        Caravan caravan = new Caravan();
        caravan.setIdCaravan(id);
        return caravan;
    }

    default Map mapMapa(Long id) {
        if (id == null) {
            return null;
        }
        Map map = new Map();
        map.setIdMap(id);
        return map;
    }
}


