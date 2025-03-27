package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.model.Caravan;
import java.util.Optional;

import java.util.List;

public interface CaravanaServiceJ {
    void borrarCaravana (Long id);
    Caravan getCaravana (Long id);
    public List<CaravanDTO> listarCaravanas();
    public Optional<CaravanDTO> buscarCaravana(Long id);
    Caravan guardarCaravana(CaravanDTO caravanDTO);
    public void updateCaravanaProductos(CaravanProductsDTO cpd);
    Optional<CaravanProductsDTO> getCaravanaProductos(Long caravanaId);



  
}
