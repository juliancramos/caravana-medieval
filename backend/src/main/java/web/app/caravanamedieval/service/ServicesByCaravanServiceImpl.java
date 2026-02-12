package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.ActiveServiceDTO;
import web.app.caravanamedieval.dto.ServicesByCaravanDTO;
import web.app.caravanamedieval.mapper.ServicesByCaravanMapper;
import web.app.caravanamedieval.model.Caravan;
import web.app.caravanamedieval.model.Services;
import web.app.caravanamedieval.model.ServicesByCaravan;
import web.app.caravanamedieval.model.ServicesByCity;
import web.app.caravanamedieval.model.keys.ServicesByCaravanKey;
import web.app.caravanamedieval.repository.CaravanRepository;
import web.app.caravanamedieval.repository.ServicesByCityRepository;
import web.app.caravanamedieval.repository.ServicesRepository;
import web.app.caravanamedieval.repository.ServicesByCaravanRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesByCaravanServiceImpl {

    private final ServicesByCaravanRepository servicesByCaravanRepository;
    private final CaravanRepository caravanRepository;
    private final ServicesRepository servicesRepository;

    private final ServicesByCityRepository servicesByCityRepository;

    @Autowired
    public ServicesByCaravanServiceImpl(ServicesByCaravanRepository servicesByCaravanRepository,
                                        CaravanRepository caravanRepository, ServicesRepository servicesRepository, ServicesByCityRepository servicesByCityRepository) {
        this.servicesByCaravanRepository = servicesByCaravanRepository;
        this.caravanRepository = caravanRepository;
        this.servicesRepository = servicesRepository;
        this.servicesByCityRepository = servicesByCityRepository;
    }



    public ServicesByCaravan assignServiceToCaravan(ServicesByCaravanDTO dto) {
        Services service = servicesRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));
        Caravan caravan = caravanRepository.findById(dto.getCaravanId())
                .orElseThrow(() -> new RuntimeException("Caravan not found"));

        ServicesByCaravanKey key = new ServicesByCaravanKey();
        key.setCaravanId(dto.getCaravanId());
        key.setServiceId(dto.getServiceId());

        if (servicesByCaravanRepository.existsById(key)) {
            throw new IllegalArgumentException("Assignment already exists");
        }

        ServicesByCaravan assignment = ServicesByCaravanMapper.INSTANCE.toEntity(dto);
        assignment.setServices(service);
        assignment.setCaravan(caravan);

        return servicesByCaravanRepository.save(assignment);
    }

    public void removeAssignment(Long serviceId, Long caravanId) {
        ServicesByCaravanKey key = new ServicesByCaravanKey(caravanId, serviceId);
        if (!servicesByCaravanRepository.existsById(key)) {
            throw new EntityNotFoundException("Assignment not found");
        }
        servicesByCaravanRepository.deleteById(key);
    }

    public ServicesByCaravan getService(Long serviceId, Long caravanId) {
        ServicesByCaravanKey key = new ServicesByCaravanKey(caravanId, serviceId);
        return servicesByCaravanRepository.findById(key)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
    }

    public List<ServicesByCaravan> getServicesByCaravanId(Long caravanId) {
        return servicesByCaravanRepository.findByCaravan_IdCaravan(caravanId);
    }

    public List<ServicesByCaravan> listServicesByCaravan() {
        return servicesByCaravanRepository.findAll();
    }

    @Transactional
    public ServicesByCaravan updateAssignment(ServicesByCaravanDTO dto) {
        ServicesByCaravanKey key = new ServicesByCaravanKey(dto.getCaravanId(), dto.getServiceId());

        ServicesByCaravan assignment = servicesByCaravanRepository.findById(key)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        ServicesByCaravanMapper.INSTANCE.updateEntity(assignment, dto);

        return servicesByCaravanRepository.save(assignment);
    }

    public List<ActiveServiceDTO> getActiveServicesByCaravanId(Long caravanId) {
        List<ServicesByCaravan> assignments = servicesByCaravanRepository.findByCaravan_IdCaravan(caravanId);
        return assignments.stream()
                .map(assignment -> {
                    Services s = assignment.getServices();
                    return new ActiveServiceDTO(s.getName(), s.getDescription(), s.getImgUrl());
                })
                .collect(Collectors.toList());
    }




}
