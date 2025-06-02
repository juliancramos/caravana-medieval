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
public class CityControllerIT {

    @Autowired
    private MockMvc mockMvc;

    // POST: crear una ciudad
    @Test
    public void testPost() throws Exception {
        mockMvc.perform(post("/city")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    // GET: obtener ciudades
    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/city"))
                .andExpect(status().isOk());
    }

    // PUT: actualizar ciudad
    @Test
    public void testPut() throws Exception {
        mockMvc.perform(put("/city")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    // DELETE: eliminar ciudad
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/city/1"))
                .andExpect(status().isNoContent());
    }
}
