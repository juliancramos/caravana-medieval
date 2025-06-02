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
public class GamesByPlayerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    // GET: obtener juegos por jugador
    @Test
    public void testGetGamesByPlayer() throws Exception {
        mockMvc.perform(get("/gamesbyplayer/1"))
                .andExpect(status().isOk());
    }

    // POST: (si se implementara alguna acción especial por jugador)
    @Test
    public void testPost() throws Exception {
        mockMvc.perform(post("/gamesbyplayer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    // PUT: (no es común aquí, pero lo dejamos de plantilla)
    @Test
    public void testPut() throws Exception {
        mockMvc.perform(put("/gamesbyplayer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    // DELETE: eliminar juegos del jugador (si se permite)
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/gamesbyplayer/1"))
                .andExpect(status().isNoContent());
    }
}
