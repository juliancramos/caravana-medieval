package web.app.caravanamedieval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.PartidaDTO;
import web.app.caravanamedieval.mapper.PartidaMapper;
import web.app.caravanamedieval.model.Caravana;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.model.Partida;
import web.app.caravanamedieval.repository.PartidaRepository;

@Service
public class PartidaServiceImpl implements PartidaService {

    @Autowired
    private PartidaMapper partidaMapper;

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

        Partida partida = partidaMapper.toEntity(partidaDTO);

        // Se asignan los objetos adicionales
        partida.setCaravana(caravana);
        partida.setMapa(mapa);

        return partidaRepository.save(partida);
    }



}
