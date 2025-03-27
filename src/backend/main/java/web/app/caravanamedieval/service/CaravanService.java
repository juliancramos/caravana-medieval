package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.CaravanDTO;
import web.app.caravanamedieval.model.Caravan;

import java.util.List;

public interface CaravanService {
    Caravan createCaravan(CaravanDTO caravanDTO);
    Caravan getCaravans(Long id);
    List<Caravan> getCaravans();
    Caravan updateCaravan(Long id, CaravanDTO caravanDTO);
    void deleteCaravan(Long id);
}
