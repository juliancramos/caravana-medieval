package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.GameDTO;
import web.app.caravanamedieval.model.Game;
import web.app.caravanamedieval.service.GameService;

import java.net.URI;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/create")
    public ResponseEntity<?> createGame(@RequestBody GameDTO gameDTO){
        try{
            Game newGame = gameService.createGame(gameDTO);

            // Crear la URI para retornar la ubicación de la partida
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{idGame}")
                    .buildAndExpand(newGame.getIdGame())
                    .toUri();
            return ResponseEntity.created(location).body(newGame);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la partida: " + e.getMessage());
        }
    }
}
