package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.ProductsByCity;
import web.app.caravanamedieval.model.keys.ProductsByCityKey;

import java.util.List;

@Repository
public interface ProductsByCityRepository extends JpaRepository<ProductsByCity, ProductsByCityKey> {
    List<ProductsByCity> findByCity_IdCity(Long cityId);
}
