package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.CiudadDTO;
import web.app.caravanamedieval.model.Ciudad;

import java.util.List;

public interface CiudadService {
    Ciudad crearCiudad(CiudadDTO ciudadDTO);
    List<CiudadDTO> listarTodas();
    CiudadDTO obtenerCiudad(Long id);
    CiudadDTO actualizarCiudad(Long id, CiudadDTO actualizado);
    void eliminarCiudad(Long id);
}
