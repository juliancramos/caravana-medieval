package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.Product;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long > {
    Optional<Product> findByName(String nombre);
}
