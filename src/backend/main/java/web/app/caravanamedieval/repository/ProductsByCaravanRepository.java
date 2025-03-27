package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.ProductsByCaravan;
import web.app.caravanamedieval.model.ProductsByCaravanKey;

import java.util.List;

@Repository
public interface ProductsByCaravanRepository extends JpaRepository<ProductsByCaravan, ProductsByCaravanKey> {
    List<ProductsByCaravan> findByCaravan_IdCaravan(Long idCaravana);
    void deleteByProduct_IdProduct(Long idProducto);
}

