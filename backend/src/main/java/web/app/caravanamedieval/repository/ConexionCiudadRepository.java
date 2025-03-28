package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.City;
import web.app.caravanamedieval.model.ConexionCiudad;

import java.util.List;

@Repository
public interface ConexionCiudadRepository extends JpaRepository<ConexionCiudad, Long> {
//    List<ConexionCiudad> findByCiudadOrigen(City cityOrigen);
//    List<ConexionCiudad> findByCiudadDestino(City cityDestino);
}

