package web.app.caravanamedieval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.model.Player;
import web.app.caravanamedieval.repository.PlayerRepository;
import web.app.caravanamedieval.security.auth.JwtAuthenticationResponse;
import web.app.caravanamedieval.dto.LoginRequest;
import web.app.caravanamedieval.dto.RegisterRequest;
import web.app.caravanamedieval.security.jwt.JwtService;
import web.app.caravanamedieval.model.Role;


@Service
public class  AuthService  {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signup(RegisterRequest request) {
        try {
            if (playerRepository.findByUsername(request.getUsername()).isPresent()) {
                throw new RuntimeException("Username already exists");
            }

            Player player = new Player();
            player.setUsername(request.getUsername());
            player.setPassword(passwordEncoder.encode(request.getPassword()));
            player.setImgUrl(request.getImgUrl());
            player.setRole(request.getRole() != null ? request.getRole() : Role.CARAVANERO);


            playerRepository.save(player);
            String jwt = jwtService.generateToken(player);
            return new JwtAuthenticationResponse(jwt);

        } catch (Exception e) {
            e.printStackTrace();  // importante: imprime el stacktrace real
            throw new RuntimeException("Signup failed: " + e.getMessage());
        }
    }

    public JwtAuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Player player = playerRepository.findByUsername(request.getUsername())
                .orElseThrow();
        String jwt = jwtService.generateToken(player);

        System.out.println("JWT generado: " + jwt);

        return new JwtAuthenticationResponse(jwt);
    }
}

/*import org.springframework.stereotype.Service;
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
}*/
