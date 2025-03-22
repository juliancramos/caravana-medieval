package web.app.caravanamedieval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.GameDTO;
import web.app.caravanamedieval.mapper.GameMapper;
import web.app.caravanamedieval.model.Caravan;
import web.app.caravanamedieval.model.Map;
import web.app.caravanamedieval.model.Game;
import web.app.caravanamedieval.repository.GameRepository;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private MapService mapService;

    @Autowired
    private CaravanService caravanService;



    @Override
    public Game createGame(GameDTO gameDTO) {
        Caravan caravan = caravanService.getCaravans(gameDTO.getCaravanId());
        Map map = mapService.getMap(gameDTO.getMapId());

        Game game = gameMapper.toEntity(gameDTO);

        // Se asignan los objetos adicionales
        game.setCaravan(caravan);
        game.setMap(map);

        return gameRepository.save(game);
    }



}
