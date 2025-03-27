package web.app.caravanamedieval.service;

import org.springframework.beans.factory.annotation.Autowired;
import web.app.caravanamedieval.model.Service;
import web.app.caravanamedieval.repository.ServiceRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServicesServiceImpl implements ServicesService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public List<Service> getServices() {
        return serviceRepository.findAll();
    }
    

    @Override
    public Service findServiceById(Integer id) {
        Optional<Service> servicio = serviceRepository.findById(id);
        return servicio.orElse(null);
    }

    @Override
    public Service createService(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public Service updateService(Service service) {
        if (serviceRepository.existsById(service.getIdService())) {
            return serviceRepository.save(service);
        }
        return null;
    }

    @Override
    public boolean deleteService(Integer id) {
        if (serviceRepository.existsById(id)) {
            serviceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
