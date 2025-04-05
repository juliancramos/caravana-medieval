package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.app.caravanamedieval.model.Route;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByOriginCityIdCity(Long idCiudad);
    List<Route> findByDestinationCityIdCity(Long idCiudad);
}
