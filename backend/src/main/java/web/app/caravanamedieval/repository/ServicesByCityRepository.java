package web.app.caravanamedieval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.app.caravanamedieval.model.ServicesByCity;
import web.app.caravanamedieval.model.keys.ServicesByCityKey;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicesByCityRepository extends JpaRepository<ServicesByCity, ServicesByCityKey> {
    List<ServicesByCity> findByCity_IdCity(Long cityId);
    Optional<ServicesByCity> findByCity_IdCityAndService_idService(Long cityId, Long serviceId);





}
