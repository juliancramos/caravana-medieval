package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.app.caravanamedieval.model.Ruta;

import java.util.List;

public interface RutaRepository extends JpaRepository<Ruta, Long> {
    List<Ruta> findByCiudadOrigenIdCiudad(Long idCiudad);
    List<Ruta> findByCiudadDestinoIdCiudad(Long idCiudad);
}
