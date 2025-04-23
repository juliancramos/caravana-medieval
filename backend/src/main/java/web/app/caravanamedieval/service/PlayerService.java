package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.PlayerDTO;
import web.app.caravanamedieval.model.Player;

import java.util.List;

public interface PlayerService {
    Player createPlayer(PlayerDTO playerDTO);
    Player getPlayer(Long id);
    Player getPlayerByUsername(String username);
    List<Player> getPlayers();
    Player updatePlayer(Long id, PlayerDTO playerDTO);
    void deletePlayer(Long id);
}

