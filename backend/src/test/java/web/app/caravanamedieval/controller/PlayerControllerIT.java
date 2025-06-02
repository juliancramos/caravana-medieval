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
public class PlayerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    // POST: registrar jugador
    @Test
    public void testPost() throws Exception {
        mockMvc.perform(post("/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "Jugador1",
                        "email": "jugador1@example.com"
                    }
                """))
                .andExpect(status().isOk());
    }

    // GET: obtener todos los jugadores
    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/player"))
                .andExpect(status().isOk());
    }

    // PUT: actualizar información del jugador
    @Test
    public void testPut() throws Exception {
        mockMvc.perform(put("/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "idPlayer": 1,
                        "name": "Jugador Actualizado"
                    }
                """))
                .andExpect(status().isOk());
    }

    // DELETE: eliminar jugador por ID
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/player/1"))
                .andExpect(status().isNoContent());
    }
}
