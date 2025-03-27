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
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/caravans/products")
public class ProductsByCaravanController {

    @Autowired
    private CaravanaServiceJ caravanaService;

    @Autowired
    private ProductService productService;

    @GetMapping("/update/{idCaravan}")
    public ModelAndView editCaravanaProductosForm(@PathVariable Long idCaravan) {
        CaravanProductsDTO caravanProductsDTO = caravanaService.getCaravanaProductos(idCaravan)
                .orElseThrow();

        List<ProductoDTOJ> allProductos = productService.recuperarProductos();

        // Obtener la lista de IDs de productos ya relacionados
        List<Long> idsProductosRelacionados = caravanProductsDTO.getProducts().stream()
                .map(ProductsByCaravanDTO::getIdProduct)
                .collect(Collectors.toList());

        //mapa de cantidades
        Map<Long, Integer> cantidadesPorProducto = caravanProductsDTO.getProducts().stream()
                .collect(Collectors.toMap(ProductsByCaravanDTO::getIdProduct, ProductsByCaravanDTO::getQuantity));

        ModelAndView mav = new ModelAndView("caravana-productos-edit");
        mav.addObject("caravanaProductos", caravanProductsDTO);
        mav.addObject("allProductos", allProductos);
        mav.addObject("idsProductosRelacionados", idsProductosRelacionados);
        mav.addObject("cantidadesPorProducto", cantidadesPorProducto);
        return mav;
    }




    @PostMapping("/save")
    public RedirectView saveCaravanaProductos(@RequestParam Long idCaravana,
                                              @RequestParam(required = false) List<Long> idsProductos,
                                              @RequestParam(required = false) List<Integer> cantidades) {


        // Crea DTO con los datos recibidos
        CaravanProductsDTO cpd = new CaravanProductsDTO();
        cpd.setIdCaravan(idCaravana);
        List<ProductsByCaravanDTO> productosDTO = new ArrayList<>();
        if (idsProductos != null) {
            for (int i = 0; i < idsProductos.size(); i++) {
                ProductsByCaravanDTO productoDTO = new ProductsByCaravanDTO();
                productoDTO.setIdCaravan(idCaravana);
                productoDTO.setIdProduct(idsProductos.get(i));
                productoDTO.setQuantity(cantidades != null && cantidades.size() > i ? cantidades.get(i) : 1);

                productosDTO.add(productoDTO);
            }
        }
        cpd.setProducts(productosDTO);
        caravanaService.updateCaravanaProductos(cpd);
        return new RedirectView("/caravana/listar");
    }









}