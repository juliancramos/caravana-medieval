package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.CaravanaDTO;
import web.app.caravanamedieval.model.Caravana;

import java.util.List;

public interface CaravanaService {
    public Caravana crearCaravana(CaravanaDTO caravanaDTO);
    public Caravana getCaravana(Long id);
    public List<Caravana> getCaravanas();
    public Caravana actualizarCaravana(Long id, CaravanaDTO caravanaDTO);
    public void eliminarCaravana(Long id);
}
