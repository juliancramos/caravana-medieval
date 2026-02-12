package web.app.caravanamedieval.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.TravelDTO;
import web.app.caravanamedieval.model.Caravan;
import web.app.caravanamedieval.model.Game;
import web.app.caravanamedieval.model.Route;
import web.app.caravanamedieval.model.ServicesByCaravan;
import web.app.caravanamedieval.repository.CaravanRepository;
import web.app.caravanamedieval.repository.GameRepository;
import web.app.caravanamedieval.repository.RouteRepository;
import web.app.caravanamedieval.repository.ServicesByCaravanRepository;

import java.util.Optional;

@Service
public class TravelService {

    private final CaravanRepository caravanRepository;
    private final RouteRepository routeRepository;
    private final ServicesByCaravanRepository servicesByCaravanRepository;
    private final GameRepository gameRepository;

    @Autowired
    public TravelService(CaravanRepository caravanRepository, RouteRepository routeRepository,
                         ServicesByCaravanRepository servicesByCaravanRepository, GameRepository gameRepository) {
        this.caravanRepository = caravanRepository;
        this.routeRepository = routeRepository;
        this.servicesByCaravanRepository = servicesByCaravanRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional
    public void travel(TravelDTO dto) {
        Caravan caravan = caravanRepository.findById(dto.getCaravanId())
                .orElseThrow(() -> new RuntimeException("Caravana no encontrada"));

        Route route = routeRepository.findById(dto.getRouteId())
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

        Game game = gameRepository.findById(dto.getGameId())
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        // Validaciones principales
        //validar puntos de vida
        validateHealth(caravan.getLifePoints(), route.getDamage());
        //validar tiempo
        int remainingTime = game.getTimeLimit() - game.getElapsedTime();
        validateTime(remainingTime, route.getTravelTime());


        // Aplicar efectos del viaje
        //daño
        int damageToApply = calculateDamage(caravan.getIdCaravan(), route.getDamage());
        caravan.setLifePoints(caravan.getLifePoints() - damageToApply);
        //tiempo de viaje
        int finalTravelTime = calculateTravelTime(caravan.getIdCaravan(), route.getTravelTime());
        game.setElapsedTime(game.getElapsedTime() + finalTravelTime);



        //mover caravana
        caravan.setCurrentCity(route.getDestinationCity());

        //Guardar cambios de caravana y partida
        caravanRepository.save(caravan);
        gameRepository.save(game);
    }

    private void validateHealth(int lifePoints, int damage) {
        if (lifePoints <= damage) {
            throw new RuntimeException("La caravana no tiene suficiente vida para esta ruta");
        }
    }

    private void validateTime(int remainingTime, int travelTime) {
        if (remainingTime < travelTime) {
            throw new RuntimeException("Not enough time to travel");
        }
    }

    private int calculateDamage(Long caravanId, int routeDamage) {
        Optional<ServicesByCaravan> servicesByCaravan = servicesByCaravanRepository
                .findByCaravan_IdCaravanAndServices_NameIgnoreCase(caravanId, "Guardias");

        if (servicesByCaravan.isPresent()) {
            int reduction = Math.round(routeDamage * 0.25f);
            routeDamage -= reduction;
        }

        return Math.max(routeDamage, 0); //no puede haber daño negativo
    }

    private int calculateTravelTime(Long caravanId, int baseTravelTime) {
        Optional<ServicesByCaravan> serviceOpt = servicesByCaravanRepository
                .findByCaravan_IdCaravanAndServices_NameIgnoreCase(caravanId, "Mejorar velocidad");

        if (serviceOpt.isPresent()) {
            ServicesByCaravan service = serviceOpt.get();
            float improvementPerPurchase = service.getServices().getUpgradePerPurchase();
            int currentUpgrade = service.getCurrentUpdate();

            float totalReduction = improvementPerPurchase * currentUpgrade;
            int reducedTime = Math.round(baseTravelTime * (1 - totalReduction));
            return Math.max(reducedTime, 1);
        }

        return baseTravelTime;
    }


}
