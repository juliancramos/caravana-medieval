package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
