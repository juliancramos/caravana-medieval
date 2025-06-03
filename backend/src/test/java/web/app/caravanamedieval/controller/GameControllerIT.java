package web.app.caravanamedieval.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import web.app.caravanamedieval.dto.GameDTO;

import java.util.HashMap;
import java.util.Map;

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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String token;
    private static Long gameId;

    @BeforeEach
    void authenticate() throws Exception {
        if (token == null) {
            Map<String, String> credentials = new HashMap<>();
            credentials.put("username", "camilo");
            credentials.put("password", "123");

            String response = mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(credentials)))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            JsonNode node = objectMapper.readTree(response);
            token = node.get("token").asText();
        }
    }

    @Test
    @Order(1)
    void crearGame_deberiaRetornar201() throws Exception {
        GameDTO dto = new GameDTO(5.0, 100.0, 5000L, 1L, 1L);

        String response = mockMvc.perform(post("/game/create")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idGame").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        gameId = objectMapper.readTree(response).get("idGame").asLong();
    }

    @Test
    @Order(2)
    void listarGames_deberiaRetornar200() throws Exception {
        mockMvc.perform(get("/game/games")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void obtenerGamePorId_deberiaRetornar200() throws Exception {
        mockMvc.perform(get("/game/" + gameId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idGame").value(gameId));
    }

    @Test
    @Order(4)
    void actualizarGame_deberiaRetornar200() throws Exception {
        GameDTO updated = new GameDTO(10.0, 200.0, 8000L, 1L, 1L);

        mockMvc.perform(put("/game/update/" + gameId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.minProfit").value(8000));
    }

    @Test
    @Order(5)
    void eliminarGame_deberiaRetornar204() throws Exception {
        mockMvc.perform(delete("/game/delete/" + gameId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
