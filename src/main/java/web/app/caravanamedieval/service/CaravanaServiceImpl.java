package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.model.Caravana;
import web.app.caravanamedieval.repository.CaravanaRepository;

@Service
public class CaravanaServiceImpl implements CaravanaService {
    @Autowired
    private CaravanaRepository caravanaRepository;

    @Override
    public Caravana crearCaravana(Caravana caravana) {
        return caravanaRepository.save(caravana);
    }

    public Caravana getCaravana(Long id) {
        return caravanaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ciudad no encontrada"));
    }

}
