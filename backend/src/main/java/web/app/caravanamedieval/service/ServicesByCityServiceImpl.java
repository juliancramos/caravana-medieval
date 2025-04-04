package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.ServicesByCityDTO;
import web.app.caravanamedieval.mapper.ServicesByCityMapper;
import web.app.caravanamedieval.model.Services;
import web.app.caravanamedieval.model.ServicesByCity;
import web.app.caravanamedieval.model.City;
import web.app.caravanamedieval.model.keys.ServicesByCityKey;
import web.app.caravanamedieval.repository.ServicesByCityRepository;
import web.app.caravanamedieval.repository.CityRepository;
import web.app.caravanamedieval.repository.ServicesRepository;

import java.util.List;

@Service
public class ServicesByCityServiceImpl {

    private final ServicesByCityRepository servicesByCityRepository;
    private final CityRepository cityRepository;
    private final ServicesRepository servicesRepository;

    @Autowired
    public ServicesByCityServiceImpl(ServicesByCityRepository servicesByCityRepository,
                                     CityRepository cityRepository, ServicesRepository servicesRepository) {
        this.servicesByCityRepository = servicesByCityRepository;
        this.cityRepository = cityRepository;
        this.servicesRepository = servicesRepository;
    }

    public ServicesByCity assignServiceToCity(ServicesByCityDTO dto) {
        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found"));
        Services service = servicesRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        ServicesByCityKey key = new ServicesByCityKey(dto.getCityId(), dto.getServiceId());
        if (servicesByCityRepository.existsById(key)) {
            throw new IllegalArgumentException("Assignment already exists");
        }

        ServicesByCity assignment = ServicesByCityMapper.INSTANCE.toEntity(dto);
        assignment.setCity(city);
        assignment.setService(service);

        return servicesByCityRepository.save(assignment);
    }

    @Transactional
    public ServicesByCity updateAssignment(ServicesByCityDTO dto) {
        ServicesByCityKey key = new ServicesByCityKey(dto.getCityId(), dto.getServiceId());
        ServicesByCity assignment = servicesByCityRepository.findById(key)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        ServicesByCityMapper.INSTANCE.updateEntity(assignment, dto);
        return servicesByCityRepository.save(assignment);
    }

    public void removeAssignment(Long cityId, Long serviceId) {
        ServicesByCityKey key = new ServicesByCityKey(cityId, serviceId);
        if (!servicesByCityRepository.existsById(key)) {
            throw new EntityNotFoundException("Assignment not found");
        }
        servicesByCityRepository.deleteById(key);
    }

    public ServicesByCity getAssignment(Long cityId, Long serviceId) {
        ServicesByCityKey key = new ServicesByCityKey(cityId, serviceId);
        return servicesByCityRepository.findById(key)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));
    }

    public List<ServicesByCity> getServicesByCityId(Long cityId) {
        return servicesByCityRepository.findByCity_IdCity(cityId);
    }

    public List<ServicesByCity> listAll() {
        return servicesByCityRepository.findAll();
    }
}
