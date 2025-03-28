package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.app.caravanamedieval.dto.CaravanDTO;
import web.app.caravanamedieval.service.CaravanaServiceJ;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.app.caravanamedieval.service.ProductService;

@Controller
@RequestMapping("/caravanaj")
public class CaravanaControllerJ {

    @Autowired
    private CaravanaServiceJ caravanaService;

    @Autowired
    private ProductService productService;
    private static final Logger log = LoggerFactory.getLogger(CaravanController.class);

    @GetMapping("/listar")
    public ModelAndView listarCaravanas(){
        log.info("Lista de Caravanas");
        List<CaravanDTO> caravanas = caravanaService.listarCaravanas();
        ModelAndView modelAndView = new ModelAndView("caravanasCRUD"); // ⚠️ Sin ".html"
        modelAndView.addObject("listaCaravanas", caravanas);
        return modelAndView;
    }
    
    

    @GetMapping("/view/{idCaravana}")
    public ModelAndView buscarCaravana(@PathVariable("idCaravana") Long idCaravana){
        CaravanDTO caravana = caravanaService.buscarCaravana(idCaravana).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("caravana-view");
        modelAndView.addObject("caravana", caravana);
        return modelAndView;
    }
    
    @GetMapping("/editar/{idCaravana}")
     public ModelAndView formularioCrearCaravana(@PathVariable("idCaravana") Long idCaravana) {
        CaravanDTO caravana = caravanaService.buscarCaravana(idCaravana).orElseThrow();
         ModelAndView mav = new ModelAndView("caravana-create");
         mav.addObject("caravana", caravana);
         return mav;
     }
    
     
     @GetMapping("/crear")
     public ModelAndView formularioCrearCaravana() {
         ModelAndView mav = new ModelAndView("caravana-create");
         mav.addObject("caravana", new CaravanDTO()); // DTO vacío
         return mav;
     }



    @PostMapping("/save")
     public RedirectView guardarCaravana(@ModelAttribute CaravanDTO caravanDTO) {
         caravanaService.guardarCaravana(caravanDTO);
         return new RedirectView("/caravana/listar");
     }

     @GetMapping("/eliminar/{idCaravana}")
     public RedirectView borrarCaravana(@PathVariable Long idCaravana){
        caravanaService.borrarCaravana(idCaravana);
        return new RedirectView("/caravana/listar");
     }
}
