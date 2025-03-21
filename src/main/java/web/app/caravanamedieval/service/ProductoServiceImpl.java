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

    private final ProductoRepository productoRepository;
    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

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

    @Override
    public Producto getProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto con ID " + id + " no encontrado"));
    }

    @Override
    public Producto getProductoByNombre(String nombre) {
        return productoRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    @Override
    @Transactional
    public Producto actualizarProducto(Long id, ProductoDTO actualizado) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto con ID " + id + " no encontrado"));

        if (actualizado.getNombre() != null) {
            producto.setNombre(actualizado.getNombre());
        }
        if (actualizado.getDescripcion() != null) {
            producto.setDescripcion(actualizado.getDescripcion());
        }
        if (actualizado.getPeso() != null) {
            producto.setPeso(actualizado.getPeso());
        }

        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new EntityNotFoundException("Producto con ID " + id + " no encontrado.");
        }
        productoRepository.deleteById(id);
    }

    //Este se borra después
    @Override
    public List<ProductoDTOJ> recuperarProductos() {
        return productoRepository.findAll().stream()
                .map(ProductoMapperJ::toDTOJ)
                .toList();
    }


}
