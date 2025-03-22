package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.CaravanaDTO;
import web.app.caravanamedieval.mapper.CaravanaMapper;
import web.app.caravanamedieval.model.Caravana;
import web.app.caravanamedieval.repository.CaravanaRepository;

import java.util.List;

@Service
public class CaravanaServiceImpl implements CaravanaService {

    private final CaravanaRepository caravanaRepository;
    @Autowired
    public CaravanaServiceImpl(CaravanaRepository caravanaRepository) {
        this.caravanaRepository = caravanaRepository;
    }

    @Override
    public Caravana crearCaravana(CaravanaDTO caravanaDTO) {
        Caravana caravana = CaravanaMapper.INSTANCE.toEntity(caravanaDTO);
        return caravanaRepository.save(caravana);
    }

    @Override
    public Caravana getCaravana(Long id) {
        return caravanaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Caravana con ID " + id + " no encontrada"));
    }

    @Override
    public List<Caravana> getCaravanas() {
        return caravanaRepository.findAll();
    }

    @Override
    public Caravana actualizarCaravana(Long id, CaravanaDTO caravanaDTO) {
        Caravana caravana = caravanaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Caravana con ID " + id + " no encontrada"));

        // Mapper actualiza los cambios directamente
       CaravanaMapper.INSTANCE.updateEntity(caravana, caravanaDTO);

        return caravanaRepository.save(caravana);
    }

    @Override
    public void eliminarCaravana(Long id) {
        caravanaRepository.deleteById(id);
    }
}
