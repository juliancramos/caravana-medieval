package web.app.caravanamedieval.service;

import web.app.caravanamedieval.model.Servicio;

import java.util.List;

public interface ServicioService {

    List<Servicio> findAllServicios();

    Servicio findServicioById(Integer id);

    Servicio saveServicio(Servicio servicio);

    Servicio updateServicio(Servicio servicio);

    boolean deleteServicio(Integer id);

    
}
