package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.app.caravanamedieval.model.ServicesByCaravan;
import web.app.caravanamedieval.model.keys.ServicesByCaravanKey;

import java.util.List;
import java.util.Optional;

public interface ServicesByCaravanRepository extends JpaRepository<ServicesByCaravan, ServicesByCaravanKey> {
    List<ServicesByCaravan> findByCaravan_IdCaravan(Long idCaravan);
    Optional<ServicesByCaravan> findByCaravan_IdCaravanAndServices_NameIgnoreCase(Long caravanId, String name);

}
