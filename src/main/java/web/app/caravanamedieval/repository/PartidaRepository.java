package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
}
