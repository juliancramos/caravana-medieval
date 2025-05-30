package web.app.caravanamedieval.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.GameByPlayerDTO;
import web.app.caravanamedieval.model.GamesByPlayer;
import web.app.caravanamedieval.service.GamesByPlayerServiceImpl;

import java.net.URI;
import java.security.Principal;
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

    @GetMapping("/dtos/player/{id}")
    public ResponseEntity<List<GameByPlayerDTO>> getGameDTOsByPlayer(@PathVariable Long id) {
        return ResponseEntity.ok(gamesByPlayerService.getGameDTOsByPlayer(id));
    }

    @GetMapping("/my-games")
    public ResponseEntity<List<GameByPlayerDTO>> getMyGames(Principal principal) {
        String username = principal.getName();
        Long idPlayer = gamesByPlayerService.findPlayerIdByUsername(username);
        return ResponseEntity.ok(gamesByPlayerService.getGameDTOsByPlayer(idPlayer));
    }

    @PostMapping("/my-game/{gameId}")
    public ResponseEntity<Void> assignMeToGame(@PathVariable Long gameId, Principal principal) {
        String username = principal.getName();
        Long idPlayer = gamesByPlayerService.findPlayerIdByUsername(username);
        gamesByPlayerService.assignPlayerToGame(gameId, idPlayer);
        return ResponseEntity.ok().build();
    }





}
