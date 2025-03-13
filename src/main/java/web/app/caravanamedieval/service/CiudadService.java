package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.CiudadDTO;
import web.app.caravanamedieval.model.Ciudad;

public interface CiudadService {
    Ciudad crearCiudad(Ciudad ciudad);
    public Ciudad getCiudad(Long id);

}
