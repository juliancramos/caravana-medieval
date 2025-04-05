package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {
}
