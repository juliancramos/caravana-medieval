package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.CaravanaDTO;
import web.app.caravanamedieval.model.Caravana;
import java.util.Optional;

import java.util.List;

public interface CaravanaService {
    void borrarCaravana (Long id);
    Caravana getCaravana (Long id);
    public List<CaravanaDTO> listarCaravanas();
    public Optional<CaravanaDTO> buscarCaravana(Long id);
    Caravana guardarCaravana(CaravanaDTO caravanaDTO);

  
}
