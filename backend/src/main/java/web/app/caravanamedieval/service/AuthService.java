package web.app.caravanamedieval.service;


import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.LoginRequest;
import web.app.caravanamedieval.dto.LoginResponse;
import web.app.caravanamedieval.repository.PlayerRepository;

@Service
public class AuthService {

    private final PlayerRepository playerRepository;

    public AuthService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public LoginResponse authenticate(LoginRequest request) {
        return playerRepository.findByUsername(request.getUsername())
                .filter(player -> player.getPassword().equals(request.getPassword()))
                .map(player -> new LoginResponse(
                        player.getIdPlayer(),
                        player.getUsername(),
                        player.getRole()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    }
}
