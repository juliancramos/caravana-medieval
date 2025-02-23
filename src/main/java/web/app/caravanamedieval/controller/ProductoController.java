package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.model.Producto;
import web.app.caravanamedieval.service.ProductoService;

import java.net.URI;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/crearProducto")
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto){
        try{
            Producto nuevoProducto = productoService.crearProducto(producto);

            // Crear la URI para retornar la ubicación del producto
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{nombre}")
                    .buildAndExpand(nuevoProducto.getNombre())
                    .toUri();
            return ResponseEntity.created(location).body(nuevoProducto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el producto: " + e.getMessage());
        }
    }

    @PostMapping("/agregarRegistros")
    public ResponseEntity<String> agregarRegistros() {
        try {
            for (int i = 0; i < 50; i++) {
                String nombre = "producto" + i;
                String descripcion = "Este es el producto " + i;
                productoService.crearProducto(new Producto(nombre, descripcion, (float) (20.5+i)));
            }
            return ResponseEntity.ok("Se agregaron 50 registros correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar registros: " + e.getMessage());
        }
    }
}
