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
public class CaravanControllerIT {

    @Autowired
    private MockMvc mockMvc;

    // POST: crear una caravana
    @Test
    public void testPost() throws Exception {
        mockMvc.perform(post("/caravan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    // GET: obtener caravanas
    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/caravan"))
                .andExpect(status().isOk());
    }

    // PUT: actualizar caravana
    @Test
    public void testPut() throws Exception {
        mockMvc.perform(put("/caravan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    // DELETE: eliminar caravana
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/caravan/1"))
                .andExpect(status().isNoContent());
    }
}
