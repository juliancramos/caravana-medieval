package web.app.caravanamedieval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.model.Jugador;
import web.app.caravanamedieval.repository.JugadorRepository;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private JugadorRepository jugadorRepository;
   
    public ResponseEntity<Map<String, Object>> authenticate(String username, String password) { /*
        Optional<Jugador> optionalJugador = jugadorRepository.findByUsername(username);
        Map<String, Object> response = new HashMap<>();
        
        if (optionalJugador.isPresent()) {
            Jugador jugador = optionalJugador.get();
            if (jugador.getPassword().equals(password)) {
                response.put("success", true);
                response.put("message", "Login exitoso");
                return ResponseEntity.ok(response);
            }
        }
        
        response.put("success", false);
        response.put("message", "Credenciales incorrectas");
        return ResponseEntity.badRequest().body(response);*/
    }
}
