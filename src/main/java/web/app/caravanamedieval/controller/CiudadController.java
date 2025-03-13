package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.CiudadDTO;
import web.app.caravanamedieval.mapper.CiudadMapper;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.service.CiudadService;
import web.app.caravanamedieval.service.MapaService;

import java.net.URI;

@RestController
@RequestMapping("/ciudad")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private MapaService mapaService;

    @Autowired
    private CiudadMapper ciudadMapper;

    @PostMapping("/crearCiudad")
    public ResponseEntity<?> crearCiudad(@RequestBody CiudadDTO ciudadDTO) {
        try {
            // Obtener el mapa directamente en el controlador
            Mapa mapa = mapaService.getMapa(ciudadDTO.getMapaId());

            // Convertir el DTO a entidad y asignar el mapa
            Ciudad ciudad = ciudadMapper.toEntity(ciudadDTO);
            ciudad.setMapa(mapa);

            // Guardar la ciudad usando el servicio
            Ciudad nuevaCiudad = ciudadService.crearCiudad(ciudad);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{nombre}")
                    .buildAndExpand(nuevaCiudad.getNombre())
                    .toUri();

            return ResponseEntity.created(location).body(nuevaCiudad);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la ciudad: " + e.getMessage());
        }
    }




//    @PostMapping("agregarRegistros")
//    public ResponseEntity<?> agregarRegistros() {
//        try{
//            for (int i = 0; i < 15; i++){
//                String nombre = "ciudad" + i;
//                ciudadService.crearCiudad(new Ciudad(nombre, 7+i));
//            }
//            return ResponseEntity.ok("Se agregaron 15 registros correctamente");
//        }catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error al agregar registros: " + e.getMessage());
//        }
//    }
}
