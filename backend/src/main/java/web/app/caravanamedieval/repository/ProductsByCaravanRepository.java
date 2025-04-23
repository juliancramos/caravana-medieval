package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.ProductsByCaravan;
import web.app.caravanamedieval.model.keys.ProductsByCaravanKey;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsByCaravanRepository extends JpaRepository<ProductsByCaravan, ProductsByCaravanKey> {
    List<ProductsByCaravan> findByCaravan_IdCaravan(Long idCaravana);
    Optional<ProductsByCaravan> findById_CaravanIdAndId_ProductIdOrderById_ProductId(Long caravanId, Long productId);
}

