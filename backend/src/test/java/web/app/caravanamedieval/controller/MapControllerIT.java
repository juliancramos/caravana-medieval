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

/**
 * Clase de pruebas de integración para MapController.
 * Valida la funcionalidad HTTP de los endpoints relacionados con mapas.
 */
@SpringBootTest(properties = {
        // Propiedades necesarias para conectarse a la base de datos PostgreSQL
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

    /**
     * Se ejecuta antes de cada prueba.
     * Realiza login mediante POST /auth/login y obtiene un JWT para autorización.
     */
    @BeforeEach
    void authenticate() throws Exception {
        LoginRequest loginRequest = new LoginRequest("camilo", "123");
        String json = objectMapper.writeValueAsString(loginRequest);

        MvcResult result = mockMvc.perform(post("/auth/login") // ✅ POST
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString());
        token = node.get("token").asText();
    }

    /**
     * GET /map/maps
     * Prueba que retorna todos los mapas existentes.
     */
    @Test
    void listarMapas_deberiaRetornar200() throws Exception {
        mockMvc.perform(get("/map/maps") // ✅ GET
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    /**
     *  POST /map/create
     * Prueba que permite crear un nuevo mapa y devuelve 201 (Created).
     */
    @Test
    void crearMapa_deberiaRetornar201() throws Exception {
        MapDTO dto = new MapDTO(null, "Mapa Test", "Descripción de prueba", Collections.emptyList());
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/map/create") // ✅ POST
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idMap").exists());
    }

    /**
     *  GET /map/{id}
     * Prueba que obtiene un mapa específico por su ID.
     */
    @Test
    void obtenerMapaPorId_deberiaRetornar200() throws Exception {
        Long idExistente = 3L;
        mockMvc.perform(get("/map/" + idExistente) // ✅ GET
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idMap").value(idExistente));
    }

    /**
     *  PUT /map/update/{id}
     * Prueba que actualiza un mapa existente.
     */
    @Test
    void actualizarMapa_deberiaRetornar200() throws Exception {
        Long idExistente = 3L;
        MapDTO updated = new MapDTO(null, "Mapa Actualizado", "Descripción actualizada", Collections.emptyList());
        String json = objectMapper.writeValueAsString(updated);

        mockMvc.perform(put("/map/update/" + idExistente) // ✅ PUT
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mapa Actualizado"));
    }

    /**
     *  POST /map/assign/{mapId}/city/{cityId}
     * Prueba que asigna una ciudad a un mapa.
     * Requiere que la ciudad no esté ya asignada al mapa para obtener 200.
     */
    @Test
    void asignarCiudadYaAsignada_deberiaRetornar400() throws Exception {
        Long mapId = 5L;   // Ya tiene la ciudad 8
        Long cityId = 8L;  // Ya está asignada

        mockMvc.perform(post("/map/assign/" + mapId + "/city/" + cityId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("La ciudad ya está asignada a este mapa"));
    }


}
