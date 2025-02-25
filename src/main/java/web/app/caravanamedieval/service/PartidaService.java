package web.app.caravanamedieval.service;


import web.app.caravanamedieval.dto.PartidaDTO;
import web.app.caravanamedieval.model.Partida;

public interface PartidaService {
    public Partida crearPartida(PartidaDTO partidaDTO);
}
