package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.app.caravanamedieval.model.GamesByPlayer;
import web.app.caravanamedieval.model.GamesByPlayerKey;

public interface GamesByPlayerRepository extends JpaRepository<GamesByPlayer, GamesByPlayerKey> {
}
