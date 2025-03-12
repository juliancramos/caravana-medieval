package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.mapper.*;
import web.app.caravanamedieval.model.Producto;
import web.app.caravanamedieval.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import java.util.List;
@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional
    public Producto crearProducto(ProductoDTO productoDTO) {
        Producto producto = ProductoMapper.INSTANCE.toEntity(productoDTO);
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> listarTodos() {
            return productoRepository.findAll();
        }
    
        //BORRAR
        @Override
        public List<ProductoDTOJ> recuperarProductos(){
            return productoRepository.findAll().stream()
                    .map(producto -> ProductoMapperJ.INSTANCE.toDTOJ(producto)) // 🔹 Llamada correcta
                    .toList();
        }
        

    @Override
    public Producto getProductoByNombre(String nombre) {
        return productoRepository.findByNombre(nombre)
                .orElseThrow(()->new EntityNotFoundException("Producto no encontrado"));
    }

    @Override
    public Producto actualizarProducto(Long id, ProductoDTO actualizado) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Producto no encontrado"));

        //Para verificar si se modificó un campo
        boolean modificacion = false;

        if(actualizado.getNombre() != null){
            producto.setNombre(actualizado.getNombre());
            modificacion = true;
        }
        if(actualizado.getDescripcion() != null){
            producto.setDescripcion(actualizado.getDescripcion());
            modificacion = true;
        }
        if(actualizado.getPeso() != null){
            producto.setPeso(actualizado.getPeso());
            modificacion = true;
        }
        if (!modificacion) {
            throw new IllegalArgumentException("No se proporcionó ningún campo válido para actualizar");
        }

        return productoRepository.save(producto);
    }

//    @Override
//    public Producto actualizarProductoEntero(Long id, Producto actualizado) {
//        Producto producto = productoRepository.findById(id)
//                .orElseThrow(()->new EntityNotFoundException("Producto no encontrado"));
//
//        //Evitar datos nulos
//        if (actualizado.getNombre() != null) {
//            producto.setNombre(actualizado.getNombre());
//        }
//        if (actualizado.getDescripcion() != null) {
//            producto.setDescripcion(actualizado.getDescripcion());
//        }
//        if (actualizado.getPeso() != null) {
//            producto.setPeso(actualizado.getPeso());
//        }
//        return productoRepository.save(producto);
//    }

    @Override
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new EntityNotFoundException("Producto con ID " + id + " no encontrado.");
        }
        productoRepository.deleteById(id);
    }

    public Producto getProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }
}
