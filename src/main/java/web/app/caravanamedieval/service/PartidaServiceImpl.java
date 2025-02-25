package web.app.caravanamedieval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.PartidaDTO;
import web.app.caravanamedieval.model.Caravana;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.model.Partida;
import web.app.caravanamedieval.repository.PartidaRepository;

@Service
public class PartidaServiceImpl implements PartidaService {
    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private MapaService mapaService;

    @Autowired
    private CaravanaService caravanaService;

    @Override
    public Partida crearPartida(PartidaDTO partidaDTO) {

        Caravana caravana = caravanaService.getCaravana(partidaDTO.getCaravanaId());

        Mapa mapa = mapaService.getMapa(partidaDTO.getMapaId());

        Partida partida = new Partida(partidaDTO.getTiempoTranscurrido(), partidaDTO.getTiempoLimite()
            , partidaDTO.getGananciaMinima(),caravana, mapa);


        return partidaRepository.save(partida);
    }


}
