package com.example.gestor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RouteProbeTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void literalAndVariableRoutesResolveAsExpected() throws Exception {
        mockMvc.perform(get("/diagnostico-rutas/nueva"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ruta literal"));

        mockMvc.perform(get("/diagnostico-rutas/7"))
                .andExpect(status().isOk())
                .andExpect(content().string("7"));

        mockMvc.perform(get("/diagnostico-rutas/abc"))
                .andExpect(status().isBadRequest());
    }
}
