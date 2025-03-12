package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.service.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/caravana/productos")
public class ProductosXCaravanaController {

    @Autowired
    private CaravanaServiceJ caravanaService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/editar/{idCaravana}")
    public ModelAndView editCaravanaProductosFormulario(@PathVariable Long idCaravana) {
        CaravanaProductosDTO caravanaProductosDTO = caravanaService.getCaravanaProductos(idCaravana)
                .orElseThrow();

        List<ProductoDTOJ> allProductos = productoService.recuperarProductos();

        // Obtener la lista de IDs de productos ya relacionados
        List<Long> idsProductosRelacionados = caravanaProductosDTO.getProductos().stream()
                .map(ProductosXCaravanaDTO::getIdProducto)
                .collect(Collectors.toList());

        ModelAndView mav = new ModelAndView("caravana-productos-edit");
        mav.addObject("caravanaProductos", caravanaProductosDTO);
        mav.addObject("allProductos", allProductos);
        mav.addObject("idsProductosRelacionados", idsProductosRelacionados);
        return mav;
    }




    @PostMapping("/save")
    public RedirectView saveCaravanaProductos(@RequestParam Long idCaravana,
                                              @RequestParam(required = false) List<Long> idsProductos){
        // Crear el DTO completo a partir de los parámetros simples
        CaravanaProductosDTO cpd = new CaravanaProductosDTO();
        cpd.setIdCaravana(idCaravana);

        List<ProductosXCaravanaDTO> productosDTO = new ArrayList<>();
        if (idsProductos != null) {
            for (Long idProducto : idsProductos) {
                ProductosXCaravanaDTO productoDTO = new ProductosXCaravanaDTO();
                productoDTO.setIdCaravana(idCaravana);
                productoDTO.setIdProducto(idProducto);
                productoDTO.setCantidad(1); // Valor predeterminado o puedes incluir un campo para esto en el formulario
                productosDTO.add(productoDTO);
            }
        }
        cpd.setProductos(productosDTO);

        caravanaService.updateCaravanaProductos(cpd);
        return new RedirectView("/caravana/listar");
    }


}