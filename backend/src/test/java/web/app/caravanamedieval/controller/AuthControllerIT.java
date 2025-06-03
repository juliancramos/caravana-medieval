package web.app.caravanamedieval.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Clase de pruebas de integración HTTP para el controlador AuthController
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa el perfil de pruebas (si tienes configuraciones específicas para test)
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc; // Permite simular peticiones HTTP

    @Autowired
    private ObjectMapper objectMapper; // Serializa objetos a JSON

    /**
     * ✅ Prueba de inicio de sesión exitoso
     * Método: POST
     * Endpoint: /auth/login
     * Descripción: Verifica que un usuario válido pueda autenticarse y obtener un token JWT.
     * Entrada: {"username": "camilo", "password": "123"}
     * Salida esperada: HTTP 200 OK y un campo "token" en el cuerpo de respuesta.
     */
    @Test
    void loginUsuarioExistente_retornaToken() throws Exception {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("username", "camilo");
        loginData.put("password", "123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    /**
     * ❌ Prueba de login inválido
     * Método: POST
     * Endpoint: /auth/login
     * Descripción: Verifica que si el usuario no existe o las credenciales son incorrectas,
     *              se retorne un error controlado con un mensaje adecuado.
     * Entrada: {"username": "noexiste", "password": "clavefalsa"}
     * Salida esperada: HTTP 500 Internal Server Error y mensaje personalizado.
     */
    @Test
    void loginInvalido_retornaErrorControlado() throws Exception {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("username", "noexiste");
        loginData.put("password", "clavefalsa");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error interno del servidor: Bad credentials"));
    }

    /**
     * 🆕 Prueba de registro de nuevo usuario
     * Método: POST
     * Endpoint: /auth/signup
     * Descripción: Verifica que un nuevo usuario pueda registrarse y recibir un token JWT tras el registro.
     * Entrada: {"username": "test_{random}", "password": "123456", "email": "test_{random}@correo.com"}
     * Salida esperada: HTTP 200 OK y un campo "token" en la respuesta.
     */
    @Test
    void registroNuevoUsuario_debeRetornarToken() throws Exception {
        Map<String, String> registerData = new HashMap<>();
        long random = System.currentTimeMillis(); // Genera datos únicos para evitar conflictos
        registerData.put("username", "test_" + random);
        registerData.put("password", "123456");
        registerData.put("email", "test_" + random + "@correo.com");

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}
