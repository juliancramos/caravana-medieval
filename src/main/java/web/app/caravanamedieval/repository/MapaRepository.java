package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.Mapa;

@Repository
public interface MapaRepository extends JpaRepository<Mapa, Long> {
}
