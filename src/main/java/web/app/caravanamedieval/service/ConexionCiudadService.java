package web.app.caravanamedieval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.ConexionCiudadDTO;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.ConexionCiudad;
import web.app.caravanamedieval.repository.ConexionCiudadRepository;

import java.util.List;

@Service
public class ConexionCiudadService {
    @Autowired
    private ConexionCiudadRepository conexionCiudadRepository;

    @Autowired
    private CiudadService ciudadService;

    public ConexionCiudad crearConexion(ConexionCiudadDTO conexionCiudadDTO) {
        Ciudad ciudadOrigen = ciudadService.getCiudad(conexionCiudadDTO.getCiudadOrigenId());

        Ciudad ciudadDestino = ciudadService.getCiudad(conexionCiudadDTO.getCiudadDestinoId());

        ConexionCiudad conexion = new ConexionCiudad(ciudadOrigen, ciudadDestino, conexionCiudadDTO.getTiempoTrayecto());
        return conexionCiudadRepository.save(conexion);
    }


}
