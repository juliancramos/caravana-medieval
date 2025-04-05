package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.ServicesDTO;
import web.app.caravanamedieval.mapper.ServicesMapper;
import web.app.caravanamedieval.model.Services;
import web.app.caravanamedieval.repository.ServicesRepository;

import java.util.List;

@Service
public class ServicesServiceImpl implements ServicesService {

    private final ServicesRepository servicesRepository;

    @Autowired
    public ServicesServiceImpl(ServicesRepository servicesRepository) {this.servicesRepository = servicesRepository;}

    @Override
    @Transactional
    public Services createService(ServicesDTO serviceDTO) {
        Services service = ServicesMapper.INSTANCE.toEntity(serviceDTO);
        return servicesRepository.save(service);
    }

    @Override
    public List<Services> getServices() {
        return servicesRepository.findAll();
    }

    @Override
    public Services getService(Long id) {
        return servicesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public Services updateService(Long id, ServicesDTO updatedServiceDTO) {
        Services service = servicesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio con ID " + id + " no encontrado"));

        ServicesMapper.INSTANCE.updateEntity(service, updatedServiceDTO);

        return servicesRepository.save(service);
    }

    @Override
    @Transactional
    public void deleteService(Long id) {
        if (!servicesRepository.existsById(id)) {
            throw new EntityNotFoundException("Servicio con ID " + id + " no encontrado.");
        }
        servicesRepository.deleteById(id);
    }
}
