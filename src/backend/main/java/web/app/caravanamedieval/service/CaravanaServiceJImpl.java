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
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CaravanaServiceJImpl implements CaravanaServiceJ {
    @Autowired
    private CaravanRepository caravanRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductsByCaravanRepository productsByCaravanRepository;




    @Override
    public List<CaravanDTO> listarCaravanas(){
        return caravanRepository.findAll().stream().map(CaravanaMapperAnterior::toDTO).toList();
        }


    @Override
    public Optional<CaravanDTO> buscarCaravana(Long id){
        return caravanRepository.findById(id).map(CaravanaMapperAnterior::toDTO);
    }

    @Override
    public void borrarCaravana(Long id){
        caravanRepository.deleteById(id);
    }

    @Override
    public Caravan getCaravana(Long id) {
        return caravanRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Caravana no encontrada con id: " + id));
    }
    

    @Override
        public Caravan guardarCaravana(CaravanDTO caravanDTO){
            Caravan caravan = CaravanaMapperAnterior.toEntity(caravanDTO);
            return caravanRepository.save(caravan);
        }



    public Optional<CaravanProductsDTO> getCaravanaProductos(Long idCaravana) {
        Caravan caravan = caravanRepository.findById(idCaravana)
                .orElseThrow(() -> new NoSuchElementException("Caravana no encontrada"));

        List<ProductsByCaravan> productsByCaravan = productsByCaravanRepository.findByCaravan_IdCaravan(idCaravana);

        List<ProductsByCaravanDTO> productos = productsByCaravan.stream()
                .map(productoXCaravana -> new ProductsByCaravanDTO(
                        productoXCaravana.getCaravan().getIdCaravan(),
                        productoXCaravana.getProduct().getIdProduct(),
                        productoXCaravana.getQuantity()
                ))
                .collect(Collectors.toList());

        CaravanProductsDTO dto = new CaravanProductsDTO(caravan.getIdCaravan(), productos);

        return Optional.of(dto);
    }


    public void updateCaravanaProductos(CaravanProductsDTO cpd) {


        Caravan caravan = caravanRepository.findById(cpd.getIdCaravan())
                .orElseThrow(() -> new NoSuchElementException("Caravana no encontrada"));

        // Obtener relaciones existentes
        List<ProductsByCaravan> relacionesExistentes =
                productsByCaravanRepository.findByCaravan_IdCaravan(cpd.getIdCaravan());

        Map<Long, ProductsByCaravan> relacionesMap = relacionesExistentes.stream()
                .collect(Collectors.toMap(rel -> rel.getProduct().getIdProduct(), rel -> rel));

        Set<Long> productosEnviados = new HashSet<>();

        // Recorrer los productos enviados desde el formulario
        for (ProductsByCaravanDTO productoDTO : cpd.getProducts()) {
            Product product = productRepository.findById(productoDTO.getIdProduct())
                    .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));

            ProductsByCaravanKey key = new ProductsByCaravanKey(caravan.getIdCaravan(), product.getIdProduct());
            productosEnviados.add(productoDTO.getIdProduct());

            if (relacionesMap.containsKey(productoDTO.getIdProduct())) {
                // Si ya existe, actualizar cantidad si es diferente
                ProductsByCaravan relacionExistente = relacionesMap.get(productoDTO.getIdProduct());
                if (relacionExistente.getQuantity() != productoDTO.getQuantity()) {
                    //System.out.println("Actualizando cantidad del producto ID: " + productoDTO.getIdProducto());
                    relacionExistente.setQuantity(productoDTO.getQuantity());
                    productsByCaravanRepository.save(relacionExistente);
                }
            } else {
                // Si no existe, crear nueva relación
                //System.out.println("Creando nueva relación para Producto ID: " + productoDTO.getIdProducto());
                ProductsByCaravan productsByCaravan = new ProductsByCaravan();
                productsByCaravan.setId(key);
                productsByCaravan.setCaravan(caravan);
                productsByCaravan.setProduct(product);
                productsByCaravan.setQuantity(productoDTO.getQuantity());
                productsByCaravanRepository.save(productsByCaravan);
            }
        }

        // Eliminar productos deseleccionados
        for (ProductsByCaravan relacion : relacionesExistentes) {
            if (!productosEnviados.contains(relacion.getProduct().getIdProduct())) {
                //System.out.println("Eliminando relación para Producto ID: " + relacion.getProducto().getIdProducto());
                productsByCaravanRepository.delete(relacion);
            }
        }

    }








}
