// Clase de prueba de integración para el controlador PlayerController
package web.app.caravanamedieval.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import web.app.caravanamedieval.dto.LoginRequest;
import web.app.caravanamedieval.dto.PlayerDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        // Configuración de base de datos para pruebas de integración
        "spring.datasource.url=jdbc:postgresql://ep-orange-field-a56wewud-pooler.us-east-2.aws.neon.tech/caravana-medieval?sslmode=require",
        "spring.datasource.username=caravana-medieval_owner",
        "spring.datasource.password=npg_GVmcjxM3P0Sw",
        "spring.datasource.driver-class-name=org.postgresql.Driver",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect"
})
@AutoConfigureMockMvc
class PlayerControllerIT {

    // Utilizado para realizar llamadas HTTP simuladas
    @Autowired
    private MockMvc mockMvc;

    // Para convertir objetos Java a JSON y viceversa
    @Autowired
    private ObjectMapper objectMapper;

    private String token;         // Token JWT para autenticación
    private Long createdId;       // ID del jugador creado
    private String createdUsername; // Nombre de usuario del jugador creado

    // Autenticación antes de cada prueba
    @BeforeEach
    void setUp() throws Exception {
        LoginRequest loginRequest = new LoginRequest("camilo", "123");
        String json = objectMapper.writeValueAsString(loginRequest);

        // Se obtiene el token JWT con una solicitud POST a /auth/login
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        token = jsonNode.get("token").asText(); // Guardamos el token para usar en las pruebas
    }

    //  POST /player/create
    // Crea un nuevo jugador y espera una respuesta HTTP 201
    @Test
    void crearPlayer_deberiaRetornar201() throws Exception {
        String uniqueUsername = "test_" + System.currentTimeMillis(); // Asegura nombre único
        PlayerDTO dto = new PlayerDTO(uniqueUsername, "1234", "COMERCIANTE");
        String json = objectMapper.writeValueAsString(dto);

        MvcResult result = mockMvc.perform(post("/player/create")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated()) // HTTP 201
                .andReturn();

        // Guardamos el ID y username creados para las siguientes pruebas
        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString());
        createdId = node.get("idPlayer").asLong();
        createdUsername = node.get("username").asText();
    }

    //  GET /player/id/{id}
    // Recupera un jugador por ID, espera HTTP 200
    @Test
    void obtenerPlayerPorId_deberiaRetornar200() throws Exception {
        crearPlayer_deberiaRetornar201();
        mockMvc.perform(get("/player/id/" + createdId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    //  GET /player/{username}
    // Recupera un jugador por nombre de usuario
    @Test
    void obtenerPlayerPorUsername_deberiaRetornar200() throws Exception {
        crearPlayer_deberiaRetornar201();
        mockMvc.perform(get("/player/" + createdUsername)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    //  PUT /player/update/{id}
    // Actualiza la contraseña del jugador
    @Test
    void actualizarPlayer_deberiaRetornar200() throws Exception {
        crearPlayer_deberiaRetornar201();
        PlayerDTO updated = new PlayerDTO(createdUsername, "4321", "COMERCIANTE");
        String json = objectMapper.writeValueAsString(updated);

        mockMvc.perform(put("/player/update/" + createdId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    //  DELETE /player/delete/{id}
    // Elimina un jugador y espera HTTP 204 No Content
    @Test
    void eliminarPlayer_deberiaRetornar204() throws Exception {
        crearPlayer_deberiaRetornar201();
        mockMvc.perform(delete("/player/delete/" + createdId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
