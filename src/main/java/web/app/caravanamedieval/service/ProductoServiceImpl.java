package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

            @Override
        public List<Producto> listarTodos() {
            return productoRepository.findAll();
        }

    public Producto getProducto(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }
} /* 
 @Transactional
    public Usuario updateProducto(Integer id, String nombre, String descripcion, Float peso) {
        return productoRepository.findById(id).map(usuario -> {
            producto.setNombre(nombre);
            usuario.setDescripcion(descripcion);
            usuario.setPeso(peso);
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Producto not found"));
    }
*/
