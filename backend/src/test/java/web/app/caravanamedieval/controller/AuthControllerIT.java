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
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    //  POST: prueba de login o registro
    @Test
    public void testPost() throws Exception {
        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk()); // ajusta según lógica real
    }

    // GET: por si Auth tiene algo que consultar
    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/auth"))
                .andExpect(status().isOk());
    }

    // PUT: actualizar algo del auth
    @Test
    public void testPut() throws Exception {
        mockMvc.perform(put("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    // DELETE: cerrar sesión o eliminar token
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/auth/1"))
                .andExpect(status().isNoContent());
    }
}
