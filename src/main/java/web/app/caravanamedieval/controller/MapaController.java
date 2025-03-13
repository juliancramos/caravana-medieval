package web.app.caravanamedieval.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.MapaDTO;
import web.app.caravanamedieval.mapper.MapaMapper;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.service.CiudadService;
import web.app.caravanamedieval.service.MapaService;

import java.net.URI;

@RestController
@RequestMapping("mapa")
public class MapaController {
    @Autowired
    private MapaService mapaService;

    @Autowired
    private CiudadService ciudadService;
    @PostMapping("/crearMapa")
    public ResponseEntity<?> crearMapa(@RequestBody MapaDTO mapaDTO) {
        try {
            Mapa nuevoMapa = mapaService.crearMapa(mapaDTO);
            MapaDTO respuestaDTO = MapaMapper.INSTANCE.toDTO(nuevoMapa);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{nombre}")
                    .buildAndExpand(respuestaDTO.getNombre())
                    .toUri();
            return ResponseEntity.created(location).body(respuestaDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el mapa: " + e.getMessage());
        }
    }


//    @PostMapping("agregarRegistros")
//    public ResponseEntity<?> agregarRegistros() {
//        try{
//            for (int i = 1; i <= 3; i++){
//                String nombre = "mapa" + i;
//                String descripcion = "Este es el mapa " + i;
//                mapaService.crearMapa(new Mapa(nombre, descripcion));
//            }
//            return ResponseEntity.ok("Se agregaron 3 registros correctamente");
//        }catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error al agregar registros: " + e.getMessage());
//        }
//    }

    @PostMapping("/asignar/{mapaId}/ciudad/{ciudadId}")
    public ResponseEntity<String> asignarCiudad(@PathVariable Long mapaId, @PathVariable Long ciudadId) {
        try {
            // Obtener el mapa y la ciudad directamente en el controlador
            Mapa mapa = mapaService.getMapa(mapaId);
            Ciudad ciudad = ciudadService.getCiudad(ciudadId);

            // Asignar la ciudad al mapa a través del servicio
            mapaService.asignarCiudadAMapa(mapa, ciudad);

            return ResponseEntity.ok("Ciudad asignada al mapa correctamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al asignar ciudad: " + e.getMessage());
        }
    }


//    @PostMapping("agregarRegistrosCiudadAMapa")
//    public ResponseEntity<?> agregarRegistrosCiudadAMapa() {
//        try{
//            //Agrega las 7 primeras ciudades al primer mapa
//            for (int i = 1; i <= 7; i++){
//                mapaService.asignarCiudadAMapa((long) 1, (long) i);
//            }
//            //Agrega las 5 siguientes ciudades al segundo mapa
//            for (int i = 8; i <= 12; i++){
//                mapaService.asignarCiudadAMapa((long) 2, (long) i);
//            }
//            //Agrega las 3 siguientes ciudades al tercer mapa
//            for (int i = 13; i <= 15; i++){
//                mapaService.asignarCiudadAMapa((long) 3, (long) i);
//            }
//
//            return ResponseEntity.ok("Se agregaron los registros correctamente");
//        }catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error al agregar registros: " + e.getMessage());
//        }
//    }


}
