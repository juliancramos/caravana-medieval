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
        //System.out.println("🔹 Iniciando actualización de productos para la caravana ID: " + cpd.getIdCaravana());

        Caravana caravana = caravanaRepository.findById(cpd.getIdCaravana())
                .orElseThrow(() -> new NoSuchElementException("❌ Caravana no encontrada"));

        // Obtener relaciones existentes
        List<ProductosXCaravana> relacionesExistentes =
                productosXCaravanaRepository.findByCaravana_IdCaravana(cpd.getIdCaravana());
        //System.out.println("🔹 Relaciones existentes: " + relacionesExistentes.size());

        Map<Long, ProductosXCaravana> relacionesMap = relacionesExistentes.stream()
                .collect(Collectors.toMap(rel -> rel.getProducto().getIdProducto(), rel -> rel));

        Set<Long> productosEnviados = new HashSet<>();

        // Recorrer los productos enviados desde el formulario
        for (ProductosXCaravanaDTO productoDTO : cpd.getProductos()) {
            Producto producto = productoRepository.findById(productoDTO.getIdProducto())
                    .orElseThrow(() -> new NoSuchElementException("❌ Producto no encontrado"));

            ProductosXCaravanaKey key = new ProductosXCaravanaKey(caravana.getIdCaravana(), producto.getIdProducto());
            productosEnviados.add(productoDTO.getIdProducto());

            if (relacionesMap.containsKey(productoDTO.getIdProducto())) {
                // Si ya existe, actualizar cantidad si es diferente
                ProductosXCaravana relacionExistente = relacionesMap.get(productoDTO.getIdProducto());
                if (relacionExistente.getCantidad() != productoDTO.getCantidad()) {
                    //System.out.println("🔹 Actualizando cantidad del producto ID: " + productoDTO.getIdProducto());
                    relacionExistente.setCantidad(productoDTO.getCantidad());
                    productosXCaravanaRepository.save(relacionExistente);
                }
            } else {
                // Si no existe, crear nueva relación
                //System.out.println("🟢 Creando nueva relación para Producto ID: " + productoDTO.getIdProducto());
                ProductosXCaravana productosXCaravana = new ProductosXCaravana();
                productosXCaravana.setId(key);
                productosXCaravana.setCaravana(caravana);
                productosXCaravana.setProducto(producto);
                productosXCaravana.setCantidad(productoDTO.getCantidad());
                productosXCaravanaRepository.save(productosXCaravana);
            }
        }

        // Eliminar productos deseleccionados
        for (ProductosXCaravana relacion : relacionesExistentes) {
            if (!productosEnviados.contains(relacion.getProducto().getIdProducto())) {
                //System.out.println("🛑 Eliminando relación para Producto ID: " + relacion.getProducto().getIdProducto());
                productosXCaravanaRepository.delete(relacion);
            }
        }

        //System.out.println("✅ Actualización finalizada para Caravana ID: " + cpd.getIdCaravana());
    }








}
