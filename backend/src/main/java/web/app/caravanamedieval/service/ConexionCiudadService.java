package web.app.caravanamedieval.service;

import org.springframework.stereotype.Service;

@Service
public class ConexionCiudadService {
//    @Autowired
//    private ConexionCiudadRepository conexionCiudadRepository;
//
//    @Autowired
//    private CiudadService ciudadService;
//
//    public ConexionCiudad crearConexion(ConexionCiudadDTO conexionCiudadDTO) {
//        Ciudad ciudadOrigen = ciudadService.getCiudad(conexionCiudadDTO.getCiudadOrigenId());
//
//        Ciudad ciudadDestino = ciudadService.getCiudad(conexionCiudadDTO.getCiudadDestinoId());
//
//        ConexionCiudad conexion = new ConexionCiudad(ciudadOrigen, ciudadDestino, conexionCiudadDTO.getTiempoTrayecto());
//        return conexionCiudadRepository.save(conexion);
//    }
//
//    public List<ConexionCiudad> obtenerConexionesDesdeCiudad(Long ciudadId) {
//        Ciudad ciudad = ciudadService.getCiudad(ciudadId);
//        return conexionCiudadRepository.findByCiudadOrigen(ciudad);
//    }
//
//    public List<ConexionCiudad> obtenerConexionesHaciaCiudad(Long ciudadId) {
//        Ciudad ciudad = ciudadService.getCiudad(ciudadId);
//        return conexionCiudadRepository.findByCiudadDestino(ciudad);
//    }
}
