package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.ServiceForStoreDTO;
import web.app.caravanamedieval.dto.ServicesByCityDTO;
import web.app.caravanamedieval.mapper.ServicesByCityMapper;
import web.app.caravanamedieval.model.*;
import web.app.caravanamedieval.model.keys.ServicesByCaravanKey;
import web.app.caravanamedieval.model.keys.ServicesByCityKey;
import web.app.caravanamedieval.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesByCityServiceImpl {

    private final ServicesByCityRepository servicesByCityRepository;

    private final ServicesByCaravanRepository servicesByCaravanRepository;
    private final CityRepository cityRepository;

    private final GameRepository gameRepository;
    private final ServicesRepository servicesRepository;

    private final CaravanRepository caravanRepository;

    private final ProductsByCaravanRepository productsByCaravanRepository;
    @Autowired
    public ServicesByCityServiceImpl(ServicesByCityRepository servicesByCityRepository,
                                     ServicesByCaravanRepository servicesByCaravanRepository, CityRepository cityRepository, GameRepository gameRepository, ServicesRepository servicesRepository, CaravanRepository caravanRepository, ProductsByCaravanRepository productsByCaravanRepository) {
        this.servicesByCityRepository = servicesByCityRepository;
        this.servicesByCaravanRepository = servicesByCaravanRepository;
        this.cityRepository = cityRepository;
        this.gameRepository = gameRepository;
        this.servicesRepository = servicesRepository;
        this.caravanRepository = caravanRepository;
        this.productsByCaravanRepository = productsByCaravanRepository;
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

    @Transactional
    public void buyService(Long gameId, Long serviceId) {
        // Obtener partida y caravana
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        Caravan caravan = game.getCaravan();
        Long cityId = caravan.getCurrentCity().getIdCity();

        // Buscar el servicio en la ciudad
        ServicesByCity assignment = servicesByCityRepository
                .findByCity_IdCityAndService_idService(cityId, serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Servicio no disponible en esta ciudad"));

        if (assignment.isPurchased()) {
            throw new IllegalArgumentException("Este servicio ya ha sido comprado en esta ciudad");
        }

        int price = assignment.getPrice();
        int currentGold = Math.toIntExact(caravan.getAvailableMoney());

        if (currentGold < price) {
            throw new IllegalArgumentException("Oro insuficiente para comprar este servicio");
        }

        // Obtener el servicio
        Services service = assignment.getService();

        // --- Validación global para servicios NO acumulables ---
        if (service.getName().equalsIgnoreCase("Guardias")) {
            boolean alreadyHasGuard = servicesByCaravanRepository
                    .findByCaravan_IdCaravanAndServices_NameIgnoreCase(caravan.getIdCaravan(), "Guardias")
                    .isPresent();

            if (alreadyHasGuard) {
                throw new IllegalArgumentException("El servicio 'Guardias' ya ha sido adquirido. No es posible comprarlo nuevamente.");
            }
        }

        // Buscar si ya existe la relación
        ServicesByCaravanKey key = new ServicesByCaravanKey(caravan.getIdCaravan(), serviceId);
        ServicesByCaravan existing = servicesByCaravanRepository.findById(key).orElse(null);

        int currentUpdate = 1;
        if (existing != null) {
            currentUpdate = existing.getCurrentUpdate() + 1;

            float totalImprovement = service.getUpgradePerPurchase() * currentUpdate;
            if (totalImprovement > service.getMaxUpgrade()) {
                throw new IllegalArgumentException("No puedes mejorar más este servicio");
            }

            existing.setCurrentUpdate(currentUpdate);
            applyServiceEffect(caravan, service, currentUpdate);
            servicesByCaravanRepository.save(existing);
        } else {
            // Crear la relación por primera vez
            ServicesByCaravan relation = new ServicesByCaravan();
            relation.setId(key);
            relation.setCaravan(caravan);
            relation.setServices(service);
            relation.setCurrentUpdate(currentUpdate);
            applyServiceEffect(caravan, service, currentUpdate);
            servicesByCaravanRepository.save(relation);
        }

        // Descontar oro y marcar como comprado
        caravan.setAvailableMoney((long) (currentGold - price));
        assignment.setPurchased(true);

        // Guardar cambios
        caravanRepository.save(caravan);
        servicesByCityRepository.save(assignment);
    }


    public List<ServiceForStoreDTO> getAvailableServicesByCity(Long cityId) {
        return servicesByCityRepository.findByCity_IdCity(cityId)
                .stream()
                .filter(s -> !s.isPurchased()) // Solo si no ha sido comprado
                .map(s -> new ServiceForStoreDTO(
                        s.getService().getIdService(),
                        s.getService().getName(),
                        s.getService().getDescription(),
                        s.getService().getImgUrl(),
                        s.getPrice(),
                        s.isPurchased()
                ))
                .collect(Collectors.toList());
    }

    private void applyServiceEffect(Caravan caravan, Services service, int currentUpdate) {
        String name = service.getName().toLowerCase();
        Float improvementPerPurchase = service.getUpgradePerPurchase(); // valor por compra
        Float maxImprovement = service.getMaxUpgrade(); // tope máximo

        switch (name) {
            case "reparar":
                int currentLife = caravan.getLifePoints();
                float improvementPercent = improvementPerPurchase * 100;
                int healAmount = Math.round(improvementPercent);
                int newLife = Math.min(currentLife + healAmount, 100);


                caravan.setLifePoints(newLife);
                break;

            case "mejorar capacidad":
                double currentCapacity = caravan.getMaxCapacity(); // ahora que lo tienes
                double totalMultiplier = Math.min(1 + improvementPerPurchase * currentUpdate, maxImprovement);
                caravan.setMaxCapacity(currentCapacity * totalMultiplier);
                break;

            case "mejorar velocidad":
                double currentSpeed = caravan.getSpeed(); // atributo actual de velocidad
                double totalBonus = improvementPerPurchase * currentUpdate;  // Ej: 0.1 * 3 = 0.3 (30%)

                // Aplica el aumento proporcional al baseSpeed
                double newSpeed = currentSpeed * (1 + totalBonus);

                // Aplica el tope máximo
                double maxSpeed = currentSpeed * (1 + maxImprovement);
                newSpeed = Math.min(newSpeed, maxSpeed);

                caravan.setSpeed(newSpeed);
                break;


        }
    }


    private double calculateCurrentLoad(Caravan caravan) {
        List<ProductsByCaravan> products = productsByCaravanRepository.findByCaravan_IdCaravan(caravan.getIdCaravan());
        return products.stream()
                .mapToDouble(p -> p.getProduct().getWeight() * p.getQuantity())
                .sum();
    }





}
