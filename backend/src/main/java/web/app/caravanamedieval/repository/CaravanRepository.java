package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.app.caravanamedieval.model.Caravan;

public interface CaravanRepository extends JpaRepository<Caravan, Long> {
}
