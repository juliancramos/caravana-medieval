package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.MapaDTO;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Mapa;

import java.util.List;

public interface MapaService {
    Mapa crearMapa(MapaDTO mapaDTO);
    List<Mapa> listarTodos();
    Mapa getMapa(Long id);
    Mapa actualizarMapa(Long id, MapaDTO mapaDTO);
    void eliminarMapa(Long id);

    public void asignarCiudadAMapa(Long mapaId, Long ciudadId);
}
