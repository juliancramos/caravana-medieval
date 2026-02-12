package web.app.caravanamedieval.security.auth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import web.app.caravanamedieval.model.Role;

@Getter
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
}
