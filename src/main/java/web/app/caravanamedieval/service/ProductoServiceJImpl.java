package web.app.caravanamedieval.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.ProductoDTOJ;
import web.app.caravanamedieval.mapper.ProductoMapperJ;
import web.app.caravanamedieval.model.Producto;
import web.app.caravanamedieval.repository.ProductoRepository;
import web.app.caravanamedieval.repository.ProductosXCaravanaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceJImpl implements ProductoServiceJ {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductosXCaravanaRepository productosXCaravanaRepository;

    @Override
    public List<ProductoDTOJ> listarTodos() {
        return productoRepository.findAll().stream()
                .map(ProductoMapperJ::toDTOJ)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductoDTOJ> buscarProducto(Long id) {
        return productoRepository.findById(id)
                .map(ProductoMapperJ::toDTOJ);
    }

    @Override
    public Producto guardarProducto(ProductoDTOJ productoDTO) {
        Producto producto = ProductoMapperJ.toEntity(productoDTO);
      return productoRepository.save(producto);
    }

    @Transactional
    @Override
    public void borrarProducto(Long id) {
        // Primero elimina relaciones en tabla intermedia
        productosXCaravanaRepository.deleteByProducto_IdProducto(id);
        //Luego elimina el producto
        productoRepository.deleteById(id);
    }
}
