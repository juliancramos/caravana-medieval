package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.app.caravanamedieval.model.GamesByPlayer;
import web.app.caravanamedieval.model.keys.GamesByPlayerKey;

import java.util.List;

public interface GamesByPlayerRepository extends JpaRepository<GamesByPlayer, GamesByPlayerKey> {
    List<GamesByPlayer> findByPlayer_IdPlayer(long idPlayer);
}
