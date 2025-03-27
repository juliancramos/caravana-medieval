package web.app.caravanamedieval.service;

import web.app.caravanamedieval.model.Service;

import java.util.List;

public interface ServicesService {

    List<Service> getServices();
    Service findServiceById(Integer id);
    Service createService(Service service);
    Service updateService(Service service);
    boolean deleteService(Integer id);

    
}
