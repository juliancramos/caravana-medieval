package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import web.app.caravanamedieval.dto.CaravanaDTO;
import web.app.caravanamedieval.mapper.CaravanaMapper;
import web.app.caravanamedieval.model.Caravana;
import web.app.caravanamedieval.repository.CaravanaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CaravanaServiceImpl implements CaravanaService {
    @Autowired
    private CaravanaRepository caravanaRepository;

    public List<CaravanaDTO> listarCaravanas(){
        return caravanaRepository.findAll().stream().map(CaravanaMapper::toDTO).toList();
        }

    public Optional<CaravanaDTO> buscarCaravana(Long id){
        return caravanaRepository.findById(id).map(CaravanaMapper::toDTO);
    }

    public void borrarCaravana(Long id){
        caravanaRepository.deleteById(id);
    }

    @Override
    public Caravana getCaravana(Long id) {
        return caravanaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Caravana no encontrada con id: " + id));
    }
    

    @Override
        public Caravana guardarCaravana(CaravanaDTO caravanaDTO){
            Caravana caravana = CaravanaMapper.toEntity(caravanaDTO);
            return caravanaRepository.save(caravana);
        }



   


}
