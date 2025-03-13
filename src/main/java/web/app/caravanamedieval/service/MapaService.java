package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.MapaDTO;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Mapa;

public interface MapaService {
    public Mapa crearMapa(MapaDTO mapa);
    public Mapa getMapa(Long id);
    public void asignarCiudadAMapa(Mapa mapa, Ciudad ciudad);
}
