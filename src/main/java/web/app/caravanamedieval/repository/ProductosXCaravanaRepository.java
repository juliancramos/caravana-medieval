package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.ProductosXCaravana;
import web.app.caravanamedieval.model.ProductosXCaravanaKey;

import java.util.List;

@Repository
public interface ProductosXCaravanaRepository extends JpaRepository<ProductosXCaravana, ProductosXCaravanaKey> {
    List<ProductosXCaravana> findByCaravana_IdCaravana(Long idCaravana);
}

