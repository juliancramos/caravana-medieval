package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.CiudadDTO;
import web.app.caravanamedieval.model.Ciudad;

import java.util.List;

public interface CiudadService {
    Ciudad crearCiudad(CiudadDTO ciudadDTO);
    List<Ciudad> listarTodas();
    Ciudad getCiudad(Long id);
    Ciudad actualizarCiudad(Long id, CiudadDTO actualizado);
    void eliminarCiudad(Long id);
}
