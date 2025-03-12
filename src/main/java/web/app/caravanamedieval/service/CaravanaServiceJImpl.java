package web.app.caravanamedieval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.mapper.*;
import web.app.caravanamedieval.model.*;
import web.app.caravanamedieval.repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CaravanaServiceJImpl implements CaravanaServiceJ {
    @Autowired
    private CaravanaRepository caravanaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductosXCaravanaRepository productosXCaravanaRepository;




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



    public Optional<CaravanaProductosDTO> getCaravanaProductos(Long idCaravana) {
        Caravana caravana = caravanaRepository.findById(idCaravana)
                .orElseThrow(() -> new NoSuchElementException("Caravana no encontrada"));


        List<ProductosXCaravana> productosXCaravana = productosXCaravanaRepository.findByCaravana_IdCaravana(idCaravana);

        List<ProductosXCaravanaDTO> productos = productosXCaravana.stream()
                .map(productoXCaravana -> new ProductosXCaravanaDTO(
                        productoXCaravana.getCaravana().getIdCaravana(),
                        productoXCaravana.getProducto().getIdProducto(),
                        productoXCaravana.getCantidad()
                ))
                .collect(Collectors.toList());

        CaravanaProductosDTO dto = new CaravanaProductosDTO(caravana.getIdCaravana(), productos);

        return Optional.of(dto);
    }

    public void updateCaravanaProductos(CaravanaProductosDTO cpd) {

        // Obtener la caravana
        Caravana caravana = caravanaRepository.findById(cpd.getIdCaravana()).orElseThrow();

        // Obtener relaciones existentes
        List<ProductosXCaravana> relacionesExistentes =
                productosXCaravanaRepository.findByCaravana_IdCaravana(cpd.getIdCaravana());

        // Convertir a un mapa para facilitar la búsqueda por ID de producto
        Map<Long, ProductosXCaravana> relacionesMap = relacionesExistentes.stream()
                .collect(Collectors.toMap(rel -> rel.getProducto().getIdProducto(), rel -> rel));

        // Recorrer los productos del DTO
        for (ProductosXCaravanaDTO productoDTO : cpd.getProductos()) {
            Producto producto = productoRepository.findById(productoDTO.getIdProducto()).orElseThrow();

            // Crear clave compuesta
            ProductosXCaravanaKey key = new ProductosXCaravanaKey(caravana.getIdCaravana(), producto.getIdProducto());

            // Verificar si la relación ya existe
            if (relacionesMap.containsKey(productoDTO.getIdProducto())) {
                // Actualizar la cantidad si es necesario
                ProductosXCaravana relacionExistente = relacionesMap.get(productoDTO.getIdProducto());
                relacionExistente.setCantidad(productoDTO.getCantidad());
                productosXCaravanaRepository.save(relacionExistente);
            } else {
                // Crear nueva relación si no existe
                ProductosXCaravana productosXCaravana = new ProductosXCaravana();
                productosXCaravana.setId(key);
                productosXCaravana.setCaravana(caravana);
                productosXCaravana.setProducto(producto);
                productosXCaravana.setCantidad(productoDTO.getCantidad());

                // Guardar la nueva relación
                productosXCaravanaRepository.save(productosXCaravana);
            }
        }

    }




}
