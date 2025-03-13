package web.app.caravanamedieval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.app.caravanamedieval.dto.MapaDTO;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.model.Partida;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface MapaMapper {
    MapaMapper INSTANCE = Mappers.getMapper(MapaMapper.class);

    MapaDTO toDTO(Mapa mapa);

    @Mapping(target = "idMapa", ignore = true)
    @Mapping(target = "ciudades", expression = "java(mapCiudadIdsToCiudades(mapaDTO.getCiudadesIds()))")
    @Mapping(target = "partidas", expression = "java(mapPartidaIdsToPartidas(mapaDTO.getPartidasIds()))")
    Mapa toEntity(MapaDTO mapaDTO);

    default List<Ciudad> mapCiudadIdsToCiudades(List<Long> ciudadesIds) {
        return ciudadesIds.stream()
                .map(id -> {
                    Ciudad ciudad = new Ciudad();
                    ciudad.setIdCiudad(id);
                    return ciudad;
                })
                .collect(Collectors.toList());
    }

    default List<Partida> mapPartidaIdsToPartidas(List<Long> partidasIds) {
        return partidasIds.stream()
                .map(id -> {
                    Partida partida = new Partida();
                    partida.setIdPartida(id);
                    return partida;
                })
                .collect(Collectors.toList());
    }
}

