package web.app.caravanamedieval.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.model.GamesByPlayer;
import web.app.caravanamedieval.service.GamesByPlayerServiceImpl;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping ("/games-by-player")
public class GamesByPlayerController {

    private final GamesByPlayerServiceImpl gamesByPlayerService;
    public GamesByPlayerController(GamesByPlayerServiceImpl gamesByPlayerService) {
        this.gamesByPlayerService = gamesByPlayerService;
    }

    @PostMapping("/assign/games/{gameId}/players/{playerId}")
    public ResponseEntity<GamesByPlayer> assignPlayerToGame(@PathVariable Long gameId, @PathVariable Long playerId) {

        GamesByPlayer assignment = gamesByPlayerService.assignPlayerToGame(gameId, playerId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(gameId, playerId)
                .toUri();

        return ResponseEntity.created(location).body(assignment);
    }


    @DeleteMapping("/remove/games/{gameId}/players/{playerId}")
    public ResponseEntity<Void> removeAssignment(@PathVariable Long gameId, @PathVariable Long playerId) {
        gamesByPlayerService.removeAssignment(gameId, playerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<GamesByPlayer>> listAssignments() {
        return ResponseEntity.ok(gamesByPlayerService.listAssignments());
    }

    @GetMapping("/game/{idGame}/player/{idPlayer}")
    public ResponseEntity<GamesByPlayer> getGame(@PathVariable Long idGame, @PathVariable Long idPlayer){
        return ResponseEntity.ok(gamesByPlayerService.getGame(idGame, idPlayer));
    }

    @GetMapping("/games/player/{id}")
    public ResponseEntity<List<GamesByPlayer>> getGamesByPlayerId(@PathVariable Long id){
        return ResponseEntity.ok(gamesByPlayerService.getGamesByPlayer(id));
    }

}
