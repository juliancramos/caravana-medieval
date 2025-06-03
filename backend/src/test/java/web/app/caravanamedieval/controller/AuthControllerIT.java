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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    void registroNuevoUsuario_debeRetornarToken() throws Exception {
        Map<String, String> registerData = new HashMap<>();
        long random = System.currentTimeMillis();
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
