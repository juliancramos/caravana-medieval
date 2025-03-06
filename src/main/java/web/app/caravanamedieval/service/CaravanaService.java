package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.CaravanaDTO;
import web.app.caravanamedieval.model.Caravana;

public interface CaravanaService {
    public Caravana crearCaravana(CaravanaDTO caravanaDTO);
    public Caravana getCaravana(Long id);
}
