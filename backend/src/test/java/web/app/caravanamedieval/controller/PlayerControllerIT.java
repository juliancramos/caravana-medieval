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
        "spring.datasource.url=jdbc:postgresql://ep-orange-field-a56wewud-pooler.us-east-2.aws.neon.tech/caravana-medieval?sslmode=require",
        "spring.datasource.username=caravana-medieval_owner",
        "spring.datasource.password=npg_GVmcjxM3P0Sw",
        "spring.datasource.driver-class-name=org.postgresql.Driver",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect"
})
@AutoConfigureMockMvc
class PlayerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;
    private Long createdId;
    private String createdUsername;

    @BeforeEach
    void setUp() throws Exception {
        LoginRequest loginRequest = new LoginRequest("camilo", "123");
        String json = objectMapper.writeValueAsString(loginRequest);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        token = jsonNode.get("token").asText();
    }

    @Test
    void crearPlayer_deberiaRetornar201() throws Exception {
        String uniqueUsername = "test_" + System.currentTimeMillis();
        PlayerDTO dto = new PlayerDTO(uniqueUsername, "1234", "COMERCIANTE");
        String json = objectMapper.writeValueAsString(dto);

        MvcResult result = mockMvc.perform(post("/player/create")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString());
        createdId = node.get("idPlayer").asLong(); // ✅ CORREGIDO
        createdUsername = node.get("username").asText();
    }

    @Test
    void obtenerPlayerPorId_deberiaRetornar200() throws Exception {
        crearPlayer_deberiaRetornar201();
        mockMvc.perform(get("/player/id/" + createdId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerPlayerPorUsername_deberiaRetornar200() throws Exception {
        crearPlayer_deberiaRetornar201();
        mockMvc.perform(get("/player/" + createdUsername)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

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

    @Test
    void eliminarPlayer_deberiaRetornar204() throws Exception {
        crearPlayer_deberiaRetornar201();
        mockMvc.perform(delete("/player/delete/" + createdId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
