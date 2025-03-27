package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.MapDTO;
import web.app.caravanamedieval.model.Map;

import java.util.List;

public interface MapService {
    Map createMap(MapDTO mapDTO);
    List<Map> getMaps();
    Map getMap(Long id);
    Map updateMap(Long id, MapDTO mapDTO);
    void deleteMap(Long id);

    void assignCityToMap(Long mapaId, Long ciudadId);
}
