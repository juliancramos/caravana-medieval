package web.app.caravanamedieval.service;

import web.app.caravanamedieval.dto.RutaDTO;
import web.app.caravanamedieval.model.Ruta;

import java.util.List;

public interface RutaService {
    public Ruta crearRuta(RutaDTO rutaDTO);
    public List<Ruta> getRutaDesdeCiudad(long ciudadId);
    public List<Ruta> getRutaHaciaCiudad(long ciudadId);
}
