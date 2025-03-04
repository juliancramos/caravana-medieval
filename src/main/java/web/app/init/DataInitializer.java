package web.app.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import web.app.caravanamedieval.model.Jugador;
import web.app.caravanamedieval.repository.JugadorRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    private final JugadorRepository jugadorRepository;

    public DataInitializer(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    @Override
    public void run(String... args) {
       
        
        // Inserta los datos iniciales
        jugadorRepository.save(new Jugador(null, "admin", "admin123", "ADMIN", null));
        jugadorRepository.save(new Jugador(null, "player1", "player123", "USER", null));
        jugadorRepository.save(new Jugador(null, "player2", "player456", "USER", null));
    }
}
