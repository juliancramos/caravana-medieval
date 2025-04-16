package web.app.caravanamedieval.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import web.app.caravanamedieval.dto.LoginRequest;
import web.app.caravanamedieval.dto.LoginResponse;
import web.app.caravanamedieval.service.AuthService;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }

}
