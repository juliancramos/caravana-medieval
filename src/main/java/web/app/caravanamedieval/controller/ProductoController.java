package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.ProductoDTO;
import web.app.caravanamedieval.model.Producto;
import web.app.caravanamedieval.service.ProductoService;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoService productoService;
    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/crearProducto")
    public ResponseEntity<Producto> crearProducto(@RequestBody ProductoDTO productoDTO) {
        Producto nuevoProducto = productoService.crearProducto(productoDTO);

        // Crear la URI para retornar la ubicación del producto
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevoProducto.getIdProducto())
                .toUri();

        return ResponseEntity.created(location).body(nuevoProducto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable Long id) {
        Producto producto = productoService.getProducto(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Producto> getProductByName(@PathVariable String nombre) {
        Producto producto = productoService.getProductoByNombre(nombre);
        return ResponseEntity.ok(producto);
    }

    @PutMapping("/actualizarCompleto/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO nuevoProducto) {
        Producto actualizado = productoService.actualizarProducto(id, nuevoProducto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
