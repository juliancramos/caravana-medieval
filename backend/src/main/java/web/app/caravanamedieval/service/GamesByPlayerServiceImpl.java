package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.model.Game;
import web.app.caravanamedieval.model.GamesByPlayer;
import web.app.caravanamedieval.model.keys.GamesByPlayerKey;
import web.app.caravanamedieval.model.Player;
import web.app.caravanamedieval.repository.GameRepository;
import web.app.caravanamedieval.repository.GamesByPlayerRepository;
import web.app.caravanamedieval.repository.PlayerRepository;
import web.app.caravanamedieval.dto.GameByPlayerDTO;


import java.util.List;

@Service
public class GamesByPlayerServiceImpl {
    private final GamesByPlayerRepository gamesByPlayerRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public GamesByPlayerServiceImpl(GamesByPlayerRepository gamesByPlayerRepository, GameRepository gameRepository,
                                    PlayerRepository playerRepository) {
        this.gamesByPlayerRepository = gamesByPlayerRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    public GamesByPlayer assignPlayerToGame(Long gameId, Long playerId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game with ID " + gameId + " not found"));
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player with ID " + playerId + " not found"));

        GamesByPlayerKey key = new GamesByPlayerKey();
        key.setGameId(gameId);
        key.setPlayerId(playerId);

        if (gamesByPlayerRepository.existsById(key)) {
            throw new IllegalArgumentException("Assignment already exists");
        }
        GamesByPlayer assignment = new GamesByPlayer();
        assignment.setId(key);
        assignment.setGame(game);
        assignment.setPlayer(player);

        return gamesByPlayerRepository.save(assignment);
    }

    public GamesByPlayer getGame(Long gameId, Long playerId) {
        GamesByPlayerKey key = new GamesByPlayerKey(gameId, playerId);
        return gamesByPlayerRepository.findById(key)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
    }

    public List<GamesByPlayer> getGamesByPlayer(Long playerId) {
        return gamesByPlayerRepository.findByPlayer_IdPlayer(playerId);
    }

    public List<GameByPlayerDTO> getGameDTOsByPlayer(Long playerId) {
        List<GamesByPlayer> assignments = gamesByPlayerRepository.findByPlayer_IdPlayer(playerId);

        return assignments.stream()
                .map(assignment -> {
                    Game game = gameRepository.findById(assignment.getGame().getIdGame())
                            .orElse(null);
                    return new GameByPlayerDTO(game);
                })
                .toList();
    }

    public void removeAssignment(Long gameId, Long playerId) {
        GamesByPlayerKey key = new GamesByPlayerKey();
        key.setGameId(gameId);
        key.setPlayerId(playerId);
        if (!gamesByPlayerRepository.existsById(key)) {throw new EntityNotFoundException("Assignment not found");}

        gamesByPlayerRepository.deleteById(key);
    }

    public List<GamesByPlayer> listAssignments() {
        return gamesByPlayerRepository.findAll();
    }

    public Long findPlayerIdByUsername(String username) {
        Player player = playerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Player not found"));
        return player.getIdPlayer();
    }

    //busca todos los roles usados en una partida según su id
    public List<String> getRolesInGame(Long gameId) {
        List<Long> playerIds = gamesByPlayerRepository.findPlayerIdsByGameId(gameId);
        return playerRepository.findRolesByPlayerIds(playerIds);
    }


}
