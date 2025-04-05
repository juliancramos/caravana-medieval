package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.GameDTO;
import web.app.caravanamedieval.model.Game;
import web.app.caravanamedieval.model.Player;
import web.app.caravanamedieval.service.GameService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    @Autowired
    public GameController(GameService gameService) { this.gameService = gameService; }

    @PostMapping("/create")
    public ResponseEntity<Game> createGame(@RequestBody GameDTO gameDTO){
        Game newGame = gameService.createGame(gameDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newGame.getIdGame())
                .toUri();

        return ResponseEntity.created(location).body(newGame);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@PathVariable Long id){
        Game game = gameService.getGame(id);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/games")
    public ResponseEntity<List<Game>> getGames(){
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody GameDTO gameDTO){
        Game game = gameService.updateGame(id, gameDTO);
        return ResponseEntity.ok(game);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable Long id){
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}
