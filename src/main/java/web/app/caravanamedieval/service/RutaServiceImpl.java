package web.app.caravanamedieval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.RutaDTO;
import web.app.caravanamedieval.mapper.RutaMapper;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Ruta;
import web.app.caravanamedieval.repository.RutaRepository;

import java.util.List;

@Service
public class RutaServiceImpl implements  RutaService{

    @Autowired
    private RutaRepository rutaRepository;
    @Autowired
    private CiudadService ciudadService;

    @Override
    public Ruta crearRuta(RutaDTO rutaDTO) {
        Ciudad ciudadOrigen = ciudadService.getCiudad(rutaDTO.getCiudadOrigenId());
        Ciudad ciudadDestino = ciudadService.getCiudad(rutaDTO.getCiudadDestinoId());

        // Usar el mapper para crear la entidad Ruta
        Ruta ruta = RutaMapper.INSTANCE.toEntity(rutaDTO, ciudadOrigen, ciudadDestino);
        return rutaRepository.save(ruta);
    }




    @Override
    public List<Ruta> getRutaDesdeCiudad(long ciudadId) {
        return rutaRepository.findByCiudadOrigenIdCiudad(ciudadId);
    }

    @Override
    public List<Ruta> getRutaHaciaCiudad(long ciudadId) {
        return rutaRepository.findByCiudadDestinoIdCiudad(ciudadId);
    }
}
