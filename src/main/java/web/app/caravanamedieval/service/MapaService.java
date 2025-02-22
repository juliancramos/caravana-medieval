package web.app.caravanamedieval.service;

import web.app.caravanamedieval.model.Mapa;

public interface MapaService {
    public Mapa crearMapa(Mapa mapa);
    public Mapa getMapa(Long id);
    public void asignarCiudadAMapa(Long mapaId, Long ciudadId);
}
