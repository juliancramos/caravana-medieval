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
public class ProductsByCaravanControllerIT {

    @Autowired
    private MockMvc mockMvc;

    // GET: obtener productos por caravana
    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/productsbycaravan/1"))
                .andExpect(status().isOk());
    }

    // POST: agregar producto a caravana (si aplica)
    @Test
    public void testPost() throws Exception {
        mockMvc.perform(post("/productsbycaravan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    // PUT: actualizar producto de caravana (si aplica)
    @Test
    public void testPut() throws Exception {
        mockMvc.perform(put("/productsbycaravan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    // DELETE: eliminar producto de caravana (si aplica)
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/productsbycaravan/1"))
                .andExpect(status().isNoContent());
    }
}
