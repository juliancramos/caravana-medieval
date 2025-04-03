package web.app.caravanamedieval.service;


import web.app.caravanamedieval.dto.GameDTO;
import web.app.caravanamedieval.model.Game;

import java.util.List;

public interface GameService {
    Game createGame(GameDTO gameDTO);
    Game getGame(Long id);
    List<Game> getAllGames();
    Game updateGame(Long id, GameDTO gameDTO);
    void deleteGame(Long id);
}
