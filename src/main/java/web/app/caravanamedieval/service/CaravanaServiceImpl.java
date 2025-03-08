package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.mapper.*;
import web.app.caravanamedieval.model.*;
import web.app.caravanamedieval.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class CaravanaServiceImpl implements CaravanaService {
    @Autowired
    private CaravanaRepository caravanaRepository;

    @Autowired
    private ProductoRepository productoRepository;




    @Override
    public List<CaravanaDTO> listarCaravanas(){
        return caravanaRepository.findAll().stream().map(CaravanaMapper::toDTO).toList();
        }


    @Override
    public Optional<CaravanaDTO> buscarCaravana(Long id){
        return caravanaRepository.findById(id).map(CaravanaMapper::toDTO);
    }

    @Override
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

    
    @Override
        public Optional <CaravanaProductosDTO> getCaravanaProductos(Long caravanaId){
            Optional<Caravana> caravanaOpt = caravanaRepository.findById(caravanaId);
            if(caravanaOpt.isEmpty()){
                return Optional.empty();
            }

            Caravana caravana = caravanaOpt.get();
           List <Long> productoIds = caravana.getProductos().stream().map(Producto::getIdProducto).toList();

           CaravanaProductosDTO caravanaProductosDTO = new CaravanaProductosDTO(caravanaId, productoIds);
           return Optional.of(caravanaProductosDTO);
        }


    public void updateCaravanaProductos(CaravanaProductosDTO cpd){
        Caravana caravana = caravanaRepository.findById(cpd.getIdCaravana()).orElseThrow();
        List<Producto> selectedProductos = productoRepository.findAllById(cpd.getIdsProductos());
        caravana.getProductos().clear();
        caravana.getProductos().addAll(selectedProductos);
        caravanaRepository.save(caravana);
        
    }



   


}
