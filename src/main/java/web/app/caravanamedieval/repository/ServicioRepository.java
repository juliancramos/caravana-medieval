package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    // Aquí podrías añadir consultas personalizadas si las necesitas
}
