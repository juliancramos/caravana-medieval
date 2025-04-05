package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.GameDTO;
import web.app.caravanamedieval.mapper.GameMapper;
import web.app.caravanamedieval.model.Caravan;
import web.app.caravanamedieval.model.Map;
import web.app.caravanamedieval.model.Game;
import web.app.caravanamedieval.repository.CaravanRepository;
import web.app.caravanamedieval.repository.GameRepository;
import web.app.caravanamedieval.repository.MapRepository;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final MapRepository mapRepository;
    private final CaravanRepository caravanRepository;

    public GameServiceImpl(GameRepository gameRepository, MapRepository mapRepository, CaravanRepository caravanRepository) {
        this.gameRepository = gameRepository;
        this.mapRepository = mapRepository;
        this.caravanRepository = caravanRepository;
    }

    @Override
    public Game createGame(GameDTO gameDTO) {
        Game game = GameMapper.INSTANCE.toEntity(gameDTO);

        Caravan caravan = caravanRepository.findById(gameDTO.getCaravanId())
                .orElseThrow(() -> new EntityNotFoundException("Caravan con ID " + gameDTO.getCaravanId() + " no encontrada"));
        Map map = mapRepository.findById(gameDTO.getMapId())
                .orElseThrow(() -> new EntityNotFoundException("Mapa con ID " + gameDTO.getMapId() + " no encontrado"));

        game.setCaravan(caravan);
        game.setMap(map);
        return gameRepository.save(game);
    }

    @Override
    public Game getGame(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partida con ID " + id + " no encontrada"));
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game updateGame(Long id, GameDTO gameDTO) {
        Game game = gameRepository.findById(id)
                .orElseThrow( ()-> new EntityNotFoundException("Partida con ID " + id + " no encontrada"));

        GameMapper.INSTANCE.updateEntity(game, gameDTO);

        //Si se cambia el id de alguna relación se cambia manualmente
        if (gameDTO.getCaravanId() != null) {
            Caravan caravan = caravanRepository.findById(gameDTO.getCaravanId())
                    .orElseThrow(() -> new EntityNotFoundException("Caravan con ID " + gameDTO.getCaravanId() + " no encontrada"));
            game.setCaravan(caravan);
        }

        if (gameDTO.getMapId() != null) {
            Map map = mapRepository.findById(gameDTO.getMapId())
                    .orElseThrow(() -> new EntityNotFoundException("Map con ID " + gameDTO.getMapId() + " no encontrada"));
            game.setMap(map);
        }
        return gameRepository.save(game);
    }

    @Override
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }


}
