package web.app.caravanamedieval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.ProductoDTOJ;
import web.app.caravanamedieval.mapper.ProductoMapperJ;
import web.app.caravanamedieval.model.Producto;
import web.app.caravanamedieval.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceJImpl implements ProductoServiceJ {

    @Autowired
    private ProductoRepository productoRepository;

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

    @Override
    public void borrarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
