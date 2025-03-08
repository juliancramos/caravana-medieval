package web.app.caravanamedieval.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.model.*;
import web.app.caravanamedieval.service.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
@RequestMapping("/caravana/productos")
public class CaravanaProductosController{

    @Autowired
    private CaravanaService caravanaService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/editar/{idCaravana}")
    public ModelAndView editCaravanaProductosFormulario(@PathVariable Long caravanaId){
      CaravanaProductosDTO caravanaProductosDTO = caravanaService.getCaravanaProductos(caravanaId).orElseThrow();
      List<ProductoDTO> allProductos = productoService.recuperarProductos();

      ModelAndView mav = new ModelAndView("caravana-productos-edit");
      mav.addObject("caravanaProductos",caravanaProductosDTO);
      mav.addObject("allProductos",allProductos);

      return mav;

    }

    @PostMapping("/save")
    public String saveCaravanaProductos(@ModelAttribute CaravanaProductosDTO cpd){
        caravanaService.updateCaravanaProductos(cpd);
        return new RedirectView("/caravana/listar");
    }

}