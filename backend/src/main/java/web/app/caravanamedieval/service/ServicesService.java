package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.ServicesDTO;
import web.app.caravanamedieval.model.Services;

import java.util.List;

public interface ServicesService {

    Services createService(ServicesDTO serviceDTO);
    List<Services> getServices();
    Services getService(Long id);
    Services updateService(Long id, ServicesDTO updatedServiceDTO);
    void deleteService(Long id);

    
}
