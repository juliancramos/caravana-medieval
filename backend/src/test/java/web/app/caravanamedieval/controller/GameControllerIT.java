package caravanamedieval.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerIT {

    @Autowired
    private MockMvc mockMvc;

    // POST: crear juego
    @Test
    public void testPost() throws Exception {
        mockMvc.perform(post("/game/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "idPlayer": 1,
                        "startDate": "2025-06-01"
                    }
                """))
                .andExpect(status().isCreated());
    }

    // GET: obtener juego por ID
    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(get("/game/1"))
                .andExpect(status().isOk());
    }

    // GET: obtener todos los juegos
    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/game/games"))
                .andExpect(status().isOk());
    }

    // PUT: actualizar juego
    @Test
    public void testPut() throws Exception {
        mockMvc.perform(put("/game/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "idGame": 1,
                        "idPlayer": 1,
                        "startDate": "2025-06-01",
                        "endDate": "2025-06-15"
                    }
                """))
                .andExpect(status().isOk());
    }

    // DELETE: eliminar juego
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/game/delete/1"))
                .andExpect(status().isNoContent());
    }
}
