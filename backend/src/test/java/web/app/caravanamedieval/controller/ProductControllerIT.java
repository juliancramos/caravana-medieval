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
import web.app.caravanamedieval.dto.ProductDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de integración para ProductController usando MockMvc.
 * Cada prueba simula llamadas HTTP reales con autenticación JWT.
 */
@SpringBootTest(properties = {
        "spring.datasource.url=${spring_datasource_url}",
        "spring.datasource.username=${spring_datasource_username}",
        "spring.datasource.password=${spring_datasource_password}",
        "spring.datasource.driver-class-name=org.postgresql.Driver",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect"
})
@AutoConfigureMockMvc
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token; // JWT para autenticación
    private Long createdProductId; // ID del producto creado en pruebas
    private final String uniqueProductName = "ProductoTest_" + System.currentTimeMillis();

    /**
     * Se ejecuta antes de cada prueba para autenticar al usuario 'camilo'
     * y obtener el token JWT.
     */
    @BeforeEach
    void authenticate() throws Exception {
        LoginRequest loginRequest = new LoginRequest("camilo", "123");
        String json = objectMapper.writeValueAsString(loginRequest);

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk()) // Espera HTTP 200 OK
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        token = jsonNode.get("token").asText(); // Extrae el token
    }

    /**
     * Prueba HTTP POST: Crea un nuevo producto.
     * Espera HTTP 201 Created.
     */
    @Test
    void crearProduct_deberiaRetornar201() throws Exception {
        ProductDTO dto = new ProductDTO(uniqueProductName, "Producto de prueba para integración", 43.53f, "/assets/img/test-product-thumb.png");
        String json = objectMapper.writeValueAsString(dto);

        MvcResult result = mockMvc.perform(post("/product/create")
                        .header("Authorization", "Bearer " + token) // Token JWT
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated()) // Espera HTTP 201
                .andReturn();

        JsonNode responseBody = objectMapper.readTree(result.getResponse().getContentAsString());
        createdProductId = responseBody.get("idProduct").asLong(); // Guarda ID para siguientes pruebas
    }

    /**
     * Prueba HTTP GET: Consulta un producto por su ID.
     * Espera HTTP 200 OK.
     */
    @Test
    void obtenerProductPorId_deberiaRetornar200() throws Exception {
        crearProduct_deberiaRetornar201();
        mockMvc.perform(get("/product/" + createdProductId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()); // Espera HTTP 200
    }

    /**
     * Prueba HTTP GET: Consulta un producto por su nombre.
     * Espera HTTP 200 OK.
     */
    @Test
    void obtenerProductPorNombre_deberiaRetornar200() throws Exception {
        crearProduct_deberiaRetornar201();
        mockMvc.perform(get("/product/name/" + uniqueProductName)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()); // Espera HTTP 200
    }

    /**
     * Prueba HTTP PUT: Actualiza un producto existente.
     * Espera HTTP 200 OK.
     */
    @Test
    void actualizarProduct_deberiaRetornar200() throws Exception {
        crearProduct_deberiaRetornar201();
        ProductDTO updateDTO = new ProductDTO(uniqueProductName, "Descripción actualizada", 12.34f, "/assets/img/test-updated.png");
        String json = objectMapper.writeValueAsString(updateDTO);

        mockMvc.perform(put("/product/update/" + createdProductId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk()); // Espera HTTP 200
    }

    /**
     * Prueba HTTP DELETE: Elimina un producto existente.
     * Espera HTTP 204 No Content.
     */
    @Test
    void eliminarProduct_deberiaRetornar204() throws Exception {
        crearProduct_deberiaRetornar201();
        mockMvc.perform(delete("/product/delete/" + createdProductId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent()); // Espera HTTP 204
    }
}
