package web.app.caravanamedieval.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
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
        System.out.println("Solicitud recibida: " + producto);
        try{
            
            Producto nuevoProducto = productoService.crearProducto(producto);

            // Crear la URI para retornar la ubicación del producto
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(nuevoProducto.getIdProducto())
                    .toUri();
            return ResponseEntity.created(location).body(nuevoProducto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el producto: " + e.getMessage());
        }
    }

        @GetMapping("/listar")
    public ResponseEntity<?> listarProductos() {
        return ResponseEntity.ok(productoService.listarTodos());
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

    @GetMapping({"/{id}"})
    public ResponseEntity<?> getProducto(@PathVariable Integer id){
        try{
            Producto producto = productoService.getProducto(id);
            if(producto == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
            }
            return ResponseEntity.ok(producto);
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?>getProductByName(@PathVariable String nombre){
        try{
            Producto producto = productoService.getProductoByNombre(nombre);
            if(producto == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
            }
            return ResponseEntity.ok(producto);
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }

    @PutMapping("/actualizarCompleto/{id}")
    public ResponseEntity<?> actualizarProductoCompleto(@PathVariable Integer id, @RequestBody Producto nuevoProducto){

        try{
            Producto actualizado = productoService.actualizarProducto(id, nuevoProducto);

            return ResponseEntity.ok(actualizado);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el producto: " + e.getMessage());
        }
    }

    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Integer id, @RequestBody Producto nuevoProducto){
        try{
            Producto actualizado = productoService.actualizarProducto(id, nuevoProducto);
            return ResponseEntity.ok(actualizado);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el producto: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Integer id){
        try{
            productoService.eliminarProducto(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el producto: " + e.getMessage());
        }
    }
}
