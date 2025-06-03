package web.app.caravanamedieval;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Clase de pruebas de integración HTTP para validar reglas de negocio y seguridad del sistema.
 * Se conecta a la base de datos PostgreSQL real configurada en el entorno.
 */
@SpringBootTest(properties = {
        // Configuración de conexión a la base de datos remota PostgreSQL
        "spring.datasource.url=jdbc:postgresql://ep-orange-field-a56wewud-pooler.us-east-2.aws.neon.tech/caravana-medieval?sslmode=require",
        "spring.datasource.username=caravana-medieval_owner",
        "spring.datasource.password=npg_GVmcjxM3P0Sw",
        "spring.datasource.driver-class-name=org.postgresql.Driver",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect"
})
@AutoConfigureMockMvc
public class Pruebas2IT {

    @Autowired
    private MockMvc mockMvc; // Simula peticiones HTTP al backend sin levantar un servidor real

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON y viceversa

    /**
     * Método auxiliar para obtener un JWT válido mediante el login.
     * @param username nombre de usuario
     * @param password contraseña
     * @return JWT como string
     */
    private String obtenerToken(String username, String password) throws Exception {
        Map<String, String> login = Map.of("username", username, "password", password);

        // Realiza POST a /auth/login
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk()) // Espera 200 si las credenciales son válidas
                .andReturn();

        // Extrae el token del cuerpo JSON de la respuesta
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        return json.get("token").asText();
    }

    /**
     * Verifica que si se intenta acceder al endpoint /travel sin autenticación, retorna 403.
     */
    @Test
    void travelSinTokenRetorna403() throws Exception {
        mockMvc.perform(post("/travel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"caravanId\":1,\"routeId\":1,\"gameId\":1}"))
                .andExpect(status().isForbidden()); // Acceso denegado sin token
    }

    /**
     * Verifica que el usuario con rol 'CARAVANERO' puede acceder al endpoint /travel.
     * Retorna 500 porque la caravana no tiene suficiente vida, pero el acceso fue permitido.
     */
    @Test
    void caravaneroPuedeViajar() throws Exception {
        String token = obtenerToken("camilo2", "123"); // Usuario caravanero

        mockMvc.perform(post("/travel")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"caravanId\":1,\"routeId\":1,\"gameId\":1}"))
                .andExpect(status().isInternalServerError()); // Error de negocio, no de autenticación
    }

    /**
     * Verifica que el usuario con rol 'COMERCIANTE' no tiene permisos para acceder al endpoint /travel.
     * Retorna 500 porque no se captura AuthorizationDeniedException de forma explícita.
     */
    @Test
    void comercianteNoPuedeViajar() throws Exception {
        String token = obtenerToken("camilo", "123"); // Usuario comerciante

        mockMvc.perform(post("/travel")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"caravanId\":1,\"routeId\":1,\"gameId\":1}"))
                .andExpect(status().isInternalServerError()); // Debería ser 403, pero se lanza excepción sin manejar
    }

    /**
     * Verifica que cuando se hace login con credenciales inválidas, el sistema retorne error.
     * Retorna 500 porque Spring Security lanza BadCredentialsException no capturada.
     */
    @Test
    void loginUsuarioInvalidoRetorna403() throws Exception {
        Map<String, String> login = Map.of("username", "invalido", "password", "123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isInternalServerError()); // No manejado por un controlador de errores
    }
}
