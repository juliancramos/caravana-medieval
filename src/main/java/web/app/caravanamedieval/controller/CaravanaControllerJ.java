package web.app.caravanamedieval.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.app.caravanamedieval.dto.CaravanaDTO;
import web.app.caravanamedieval.dto.ProductoDTO;
import web.app.caravanamedieval.model.Caravana;
import web.app.caravanamedieval.model.Producto;
import web.app.caravanamedieval.service.CaravanaServiceJ;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.app.caravanamedieval.service.ProductoService;

@Controller
@RequestMapping("/caravana")
public class CaravanaControllerJ {

    @Autowired
    private CaravanaServiceJ caravanaService;

    @Autowired
    private ProductoService productoService;
    private static final Logger log = LoggerFactory.getLogger(CaravanaController.class);

    @GetMapping("/listar")
    public ModelAndView listarCaravanas(){
        log.info("Lista de Caravanas");
        List<CaravanaDTO> caravanas = caravanaService.listarCaravanas();
        ModelAndView modelAndView = new ModelAndView("caravanasCRUD"); // ⚠️ Sin ".html"
        modelAndView.addObject("listaCaravanas", caravanas);
        return modelAndView;
    }
    
    

    @GetMapping("/view/{idCaravana}")
    public ModelAndView buscarCaravana(@PathVariable("idCaravana") Long idCaravana){
        CaravanaDTO caravana = caravanaService.buscarCaravana(idCaravana).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("caravana-view");
        modelAndView.addObject("caravana", caravana);
        return modelAndView;
    }
    
    @GetMapping("/editar/{idCaravana}")
     public ModelAndView formularioCrearCaravana(@PathVariable("idCaravana") Long idCaravana) {
        CaravanaDTO caravana = caravanaService.buscarCaravana(idCaravana).orElseThrow();
         ModelAndView mav = new ModelAndView("caravana-create");
         mav.addObject("caravana", caravana);
         return mav;
     }
    
     
     @GetMapping("/crear")
     public ModelAndView formularioCrearCaravana() {
         ModelAndView mav = new ModelAndView("caravana-create");
         mav.addObject("caravana", new CaravanaDTO()); // DTO vacío
         return mav;
     }



    @PostMapping("/save")
     public RedirectView guardarCaravana(@ModelAttribute CaravanaDTO caravanaDTO) {
         caravanaService.guardarCaravana(caravanaDTO);
         return new RedirectView("/caravana/listar");
     }

     @GetMapping("/eliminar/{idCaravana}")
     public RedirectView borrarCaravana(@PathVariable Long idCaravana){
        caravanaService.borrarCaravana(idCaravana);
        return new RedirectView("/caravana/listar");
     }
}
