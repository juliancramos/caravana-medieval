package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import web.app.caravanamedieval.dto.ProductoDTO;
import web.app.caravanamedieval.dto.ProductoDTOJ;
import web.app.caravanamedieval.service.ProductoServiceJ;

import java.util.List;

@Controller
@RequestMapping("/producto")
public class ProductoControllerJ {

    @Autowired
    private ProductoServiceJ productoServiceJ;

    // Listar productos
    @GetMapping("/listar")
    public ModelAndView listarProductos() {
        List<ProductoDTOJ> productos = productoServiceJ.listarTodos();
        ModelAndView modelAndView = new ModelAndView("productos");
        modelAndView.addObject("productos", productos);
        return modelAndView;
    }

    // Mostrar formulario para crear un nuevo producto
    @GetMapping("/crear")
    public ModelAndView formularioCrearProducto() {
        ModelAndView mav = new ModelAndView("producto-create");
        mav.addObject("producto", new ProductoDTOJ()); // DTO vacío
        return mav;
    }

    // Editar producto (cargar formulario con datos)
    @GetMapping("/editar/{idProducto}")
    public ModelAndView formularioEditarProducto(@PathVariable("idProducto") Long idProducto) {
        ProductoDTOJ producto = productoServiceJ.buscarProducto(idProducto).orElseThrow();
        ModelAndView mav = new ModelAndView("producto-create");
        mav.addObject("producto", producto);
        return mav;
    }

    // Guardar o actualizar un producto
    @PostMapping("/save")
    public RedirectView guardarProducto(@ModelAttribute ProductoDTOJ productoDTO) {
        productoServiceJ.guardarProducto(productoDTO);
        return new RedirectView("/producto/listar");
    }

    // Eliminar producto
    @GetMapping("/eliminar/{idProducto}")
    public RedirectView borrarProducto(@PathVariable Long idProducto) {
        productoServiceJ.borrarProducto(idProducto);
        return new RedirectView("/producto/listar");
    }
}
