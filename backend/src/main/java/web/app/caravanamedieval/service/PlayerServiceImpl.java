package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.PlayerDTO;
import web.app.caravanamedieval.mapper.PlayerMapper;
import web.app.caravanamedieval.model.Player;
import web.app.caravanamedieval.repository.PlayerRepository;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}

    @Override
    public Player createPlayer(PlayerDTO playerDTO) {
        Player player = PlayerMapper.INSTANCE.toEntity(playerDTO);
        return playerRepository.save(player);
    }

    @Override
    public Player getPlayer(Long id) {
        return playerRepository.findById(id)
                .orElseThrow( () ->new EntityNotFoundException("Jugador con ID " + id + " no encontrado"));
    }

    @Override
    public Player getPlayerByUsername(String username) {
        return playerRepository.findByUsername(username)
                .orElseThrow( () ->new EntityNotFoundException("Jugador con usuario " + username + " no encontrado"));
    }

    @Override
    public List<Player> getPlayers() {return playerRepository.findAll();}

    @Override
    public Player updatePlayer(Long id, PlayerDTO playerDTO) {
        Player player = playerRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Jugador con ID " + id + " no encontrado"));

        PlayerMapper.INSTANCE.updateEntity(player, playerDTO);
        return playerRepository.save(player);
    }

    @Override
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}
