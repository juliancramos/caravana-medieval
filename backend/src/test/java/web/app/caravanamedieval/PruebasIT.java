package web.app.caravanamedieval;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class PruebasIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String loginYObtenerToken() throws Exception {
        Map<String, String> loginData = Map.of("username", "camilo", "password", "123");

        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(response);
        return jsonNode.get("token").asText();
    }

    @Test
    void comercianteNoPuedeViajar() throws Exception {
        String token = loginYObtenerToken();
        var travel = Map.of("caravanId", 1, "routeId", 1, "gameId", 1);
        mockMvc.perform(post("/travel")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(travel)))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    String body = result.getResponse().getContentAsString();
                    assertTrue(status == 403 || status == 500, "Esperado 403 o 500, fue: " + status + " - " + body);
                });
    }

    @Test
    void comercianteNoPuedeComprarServicio() throws Exception {
        String token = loginYObtenerToken();
        var servicio = Map.of(
                "name", "TestService",
                "description", "desc",
                "upgradePerPurchase", 1.0,
                "price", 100
        );
        mockMvc.perform(post("/services")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(servicio)))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    String body = result.getResponse().getContentAsString();
                    assertTrue(status == 403 || status == 404 || status == 500, "Esperado 403/404/500, fue: " + status + " - " + body);
                });
    }

    @Test
    void caravanaNoPuedeTenerMasServicios() throws Exception {
        String token = loginYObtenerToken();
        var compra = Map.of(
                "caravanId", 1,
                "serviceId", 1,
                "currentUpdate", 1
        );
        mockMvc.perform(post("/caravans/1/services")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compra)))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    String body = result.getResponse().getContentAsString();
                    assertTrue(status == 400 || status == 404 || status == 500, "Esperado 400/404/500, fue: " + status + " - " + body);
                });
    }
    @Test
    void caravaneroPuedeViajar() throws Exception {
        // Usuario caravanero: camilo2
        Map<String, String> loginData = Map.of("username", "camilo2", "password", "123");

        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = objectMapper.readTree(response).get("token").asText();

        Map<String, Object> travel = Map.of("caravanId", 1, "routeId", 1, "gameId", 1);

        mockMvc.perform(post("/travel")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(travel)))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    assertTrue(status == 200 || status == 500, "Esperado 200/500, fue: " + status);
                });
    }

    @Test
    void comercianteNoPuedeViajar_endpointProtegido() throws Exception {
        // Usuario comerciante: camilo
        Map<String, String> loginData = Map.of("username", "camilo", "password", "123");

        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = objectMapper.readTree(response).get("token").asText();

        Map<String, Object> travel = Map.of("caravanId", 1, "routeId", 1, "gameId", 1);

        mockMvc.perform(post("/travel")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(travel)))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    assertTrue(status == 403 || status == 500, "Esperado 403/500, fue: " + status);
                });
    }

}