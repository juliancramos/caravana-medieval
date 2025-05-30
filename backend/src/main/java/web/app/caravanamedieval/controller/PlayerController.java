package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.app.caravanamedieval.dto.PlayerDTO;
import web.app.caravanamedieval.model.Player;
import web.app.caravanamedieval.model.Role;
import web.app.caravanamedieval.repository.PlayerRepository;
import web.app.caravanamedieval.security.auth.JwtAuthenticationResponse;
import web.app.caravanamedieval.service.PlayerService;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {this.playerService = playerService;}

    @PostMapping("/create")
    public ResponseEntity<Player> createPlayer(@RequestBody PlayerDTO playerDTO) {
        Player newPlayer = playerService.createPlayer(playerDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/username")
                .buildAndExpand(newPlayer.getUsername())
                .toUri();
        return ResponseEntity.created(location).body(newPlayer);
    }

    @PutMapping("/update-role")
    public JwtAuthenticationResponse updateRole(@RequestParam Role role, Principal principal) {
        return playerService.updateRole(principal.getName(), role);
    }




    @GetMapping("/id/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        Player player = playerService.getPlayer(id);
        return ResponseEntity.ok(player);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Player> getPlayerByUsername(@PathVariable String username) {
        Player player = playerService.getPlayerByUsername(username);
        return ResponseEntity.ok(player);
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getPlayers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO playerDTO) {
        Player player = playerService.updatePlayer(id, playerDTO);
        return ResponseEntity.ok(player);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
