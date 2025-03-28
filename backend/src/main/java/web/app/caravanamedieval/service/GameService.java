package web.app.caravanamedieval.service;


import web.app.caravanamedieval.dto.GameDTO;
import web.app.caravanamedieval.model.Game;

public interface GameService {
    Game createGame(GameDTO gameDTO);
}
