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
public class MapControllerIT {

    @Autowired
    private MockMvc mockMvc;

    // POST: crear un mapa
    @Test
    public void testPost() throws Exception {
        mockMvc.perform(post("/map")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    // GET: obtener información del mapa
    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/map"))
                .andExpect(status().isOk());
    }

    // PUT: actualizar el mapa
    @Test
    public void testPut() throws Exception {
        mockMvc.perform(put("/map")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    // DELETE: eliminar el mapa por ID
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/map/1"))
                .andExpect(status().isNoContent());
    }
}
