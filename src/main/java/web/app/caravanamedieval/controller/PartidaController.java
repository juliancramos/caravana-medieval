package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.PartidaDTO;
import web.app.caravanamedieval.model.Partida;
import web.app.caravanamedieval.service.PartidaService;

import java.net.URI;

@RestController
@RequestMapping("/partida")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @PostMapping("/crearPartida")
    public ResponseEntity<?> crearPartida(@RequestBody PartidaDTO partidaDTO){
        try{
            Partida nuevaPartida = partidaService.crearPartida(partidaDTO);

            // Crear la URI para retornar la ubicación de la partida
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{idPartida}")
                    .buildAndExpand(nuevaPartida.getIdPartida())
                    .toUri();
            return ResponseEntity.created(location).body(nuevaPartida);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la partida: " + e.getMessage());
        }
    }
}
