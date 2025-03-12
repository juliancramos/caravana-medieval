package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.Producto;
import java.util.Optional;
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long > {
    Optional<Producto> findByNombre (String nombre);
}
