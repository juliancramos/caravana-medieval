package web.app.caravanamedieval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.model.Servicio;
import web.app.caravanamedieval.repository.ServicioRepository;
import web.app.caravanamedieval.service.ServicioService;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public List<Servicio> findAllServicios() {
        return servicioRepository.findAll();
    }
    

    @Override
    public Servicio findServicioById(Integer id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        return servicio.orElse(null);
    }

    @Override
    public Servicio saveServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio updateServicio(Servicio servicio) {
        if (servicioRepository.existsById(servicio.getIdServicio())) {
            return servicioRepository.save(servicio);
        }
        return null;
    }

    @Override
    public boolean deleteServicio(Integer id) {
        if (servicioRepository.existsById(id)) {
            servicioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
