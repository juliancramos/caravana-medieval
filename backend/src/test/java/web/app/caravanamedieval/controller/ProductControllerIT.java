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
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    // POST: crear producto
    @Test
    public void testPost() throws Exception {
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "Espada",
                        "description": "Espada de acero",
                        "price": 150.0
                    }
                """))
                .andExpect(status().isOk());
    }

    // GET: obtener productos
    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/product"))
                .andExpect(status().isOk());
    }

    // PUT: actualizar producto
    @Test
    public void testPut() throws Exception {
        mockMvc.perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "idProduct": 1,
                        "name": "Espada mejorada"
                    }
                """))
                .andExpect(status().isOk());
    }

    // DELETE: eliminar producto
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/product/1"))
                .andExpect(status().isNoContent());
    }
}
