package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.app.caravanamedieval.dto.LoginRequest;
import web.app.caravanamedieval.dto.RegisterRequest;
import web.app.caravanamedieval.service.AuthService;
import web.app.caravanamedieval.security.auth.JwtAuthenticationResponse;
import web.app.caravanamedieval.service.AuthService;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}

/*
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

}*/
