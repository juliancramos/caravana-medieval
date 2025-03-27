package web.app.caravanamedieval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.model.Player;
import web.app.caravanamedieval.repository.PlayerRepository;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private PlayerRepository playerRepository;

    public ResponseEntity<Map<String, Object>> authenticate(String username, String password) {
        Optional<Player> optionalJugador = playerRepository.findByUsername(username);
        Map<String, Object> response = new HashMap<>();
        
        if (optionalJugador.isPresent()) {
            Player player = optionalJugador.get();
            if (player.getPassword().equals(password)) {
                response.put("success", true);
                response.put("message", "Login exitoso");
                return ResponseEntity.ok(response);
            }
        }
        
        response.put("success", false);
        response.put("message", "Credenciales incorrectas");
        return ResponseEntity.badRequest().body(response);
    }
}
