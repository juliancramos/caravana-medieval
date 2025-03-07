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

@Service
public class CaravanaServiceImpl implements CaravanaService {
    @Autowired
    private CaravanaRepository caravanaRepository;

    @Override
    public Caravana crearCaravana(CaravanaDTO caravanaDTO) {
        Caravana caravana = CaravanaMapper.INSTANCE.toEntity(caravanaDTO);
        return caravanaRepository.save(caravana);
    }

    public Caravana getCaravana(Long id) {
        return caravanaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ciudad no encontrada"));
    }

    @Override
    public List<Caravana> getCaravanas() {
        List<Caravana> caravanas = caravanaRepository.findAll();
        if (caravanas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No hay caravanas registradas");
        }
        return caravanas;
    }

    @Override
    public Caravana actualizarCaravana(Long id, CaravanaDTO caravanaDTO) {
        Caravana caravana = caravanaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Caravana no encontrada"));

        boolean modificacion = false;
        if (caravanaDTO.getNombre() != null) {
            caravana.setNombre(caravanaDTO.getNombre());
            modificacion = true;
        }
        if (caravanaDTO.getVelocidad() != null) {
            caravana.setVelocidad(caravanaDTO.getVelocidad());
            modificacion = true;
        }
        if (caravanaDTO.getCapacidadMaxima() != null) {
            caravana.setCapacidadMaxima(caravanaDTO.getCapacidadMaxima());
            modificacion = true;
        }
        if (caravanaDTO.getDineroDisponible() != null) {
            caravana.setDineroDisponible(caravanaDTO.getDineroDisponible());
            modificacion = true;
        }
        if (caravanaDTO.getPuntosVida() != null) {
            caravana.setPuntosVida(caravanaDTO.getPuntosVida());
            modificacion = true;
        }

        if(!modificacion) {
            throw new IllegalArgumentException("No se proporcionó ningún cambio para actualizar la caravana");
        }
        return caravanaRepository.save(caravana);
    }

    @Override
    public void eliminarCaravana(Long id) {
        if(!caravanaRepository.existsById(id)){
            throw new EntityNotFoundException("Caravana con ID" + id + " no encontrada");
        }
        caravanaRepository.deleteById(id);
    }


}
