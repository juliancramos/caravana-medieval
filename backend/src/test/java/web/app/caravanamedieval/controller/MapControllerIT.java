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
import web.app.caravanamedieval.dto.MapDTO;

import java.util.Collections;

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
class MapControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void authenticate() throws Exception {
        LoginRequest loginRequest = new LoginRequest("camilo", "123");
        String json = objectMapper.writeValueAsString(loginRequest);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString());
        token = node.get("token").asText();
    }

    @Test
    void listarMapas_deberiaRetornar200() throws Exception {
        mockMvc.perform(get("/map/maps")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void crearMapa_deberiaRetornar201() throws Exception {
        MapDTO dto = new MapDTO(null, "Mapa Test", "Descripción de prueba", Collections.emptyList());
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/map/create")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idMap").exists());
    }

    @Test
    void obtenerMapaPorId_deberiaRetornar200() throws Exception {
        Long idExistente = 3L;
        mockMvc.perform(get("/map/" + idExistente)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idMap").value(idExistente));
    }

    @Test
    void actualizarMapa_deberiaRetornar200() throws Exception {
        Long idExistente = 3L;
        MapDTO updated = new MapDTO(null, "Mapa Actualizado", "Descripción actualizada", Collections.emptyList());
        String json = objectMapper.writeValueAsString(updated);

        mockMvc.perform(put("/map/update/" + idExistente)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mapa Actualizado"));
    }

    @Test
    void asignarCiudadAMapa_deberiaRetornar200() throws Exception {
        Long mapId = 5L;   // Usa un ID de mapa que *no* tenga aún la ciudad
        Long cityId = 4L;  // Usa un ID de ciudad *no asignada* previamente a ese mapa

        mockMvc.perform(post("/map/assign/" + mapId + "/city/" + cityId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("City assigned to the map correctly"));
    }

}
