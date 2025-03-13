package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import web.app.caravanamedieval.dto.PartidaDTO;
import web.app.caravanamedieval.model.Caravana;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.model.Partida;
@Mapper(componentModel = "spring")
public interface PartidaMapper {

    @Mapping(source = "caravana.idCaravana", target = "caravanaId")
    @Mapping(source = "mapa.idMapa", target = "mapaId")
    PartidaDTO toDTO(Partida partida);

    @Mapping(source = "caravanaId", target = "caravana")
    @Mapping(source = "mapaId", target = "mapa")
    Partida toEntity(PartidaDTO partidaDTO);

    default Caravana map(Long id) {
        if (id == null) {
            return null;
        }
        Caravana caravana = new Caravana();
        caravana.setIdCaravana(id);
        return caravana;
    }

    default Mapa mapMapa(Long id) {
        if (id == null) {
            return null;
        }
        Mapa mapa = new Mapa();
        mapa.setIdMapa(id);
        return mapa;
    }
}


