package web.app.caravanamedieval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.CiudadDTO;
import web.app.caravanamedieval.dto.RutaDTO;
import web.app.caravanamedieval.mapper.RutaContext;
import web.app.caravanamedieval.mapper.RutaMapper;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Ruta;
import web.app.caravanamedieval.repository.RutaRepository;

import java.util.List;

@Service
public class RutaServiceImpl implements  RutaService{

    private final RutaRepository rutaRepository;
    private final CiudadService ciudadService;
    private final RutaMapper rutaMapper;

    @Autowired
    public RutaServiceImpl(RutaRepository rutaRepository, CiudadService ciudadService, RutaMapper rutaMapper) {
        this.rutaRepository = rutaRepository;
        this.ciudadService = ciudadService;
        this.rutaMapper = rutaMapper;
    }

    @Override
    public Ruta crearRuta(RutaDTO rutaDTO) {
        Ciudad ciudadOrigen = ciudadService.getCiudad(rutaDTO.getCiudadOrigenId());
        Ciudad ciudadDestino = ciudadService.getCiudad(rutaDTO.getCiudadDestinoId());

        RutaContext context = new RutaContext(ciudadOrigen, ciudadDestino);

        Ruta ruta = rutaMapper.toEntity(rutaDTO, context);
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
