package com.greenbite.compras.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenbite.compras.modelo.Compra;
import com.greenbite.compras.modelo.ItemCompra;
import com.greenbite.compras.servicio.CompraServicio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompraControlador.class)
class CompraControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompraServicio servicio;

    @Test
    @DisplayName("Debería registrar una compra")
    void deberiaRegistrarCompra() throws Exception {

        Compra compra = new Compra();
        compra.setItems(List.of(new ItemCompra(1L, 2, 10000.0)));
        compra.setTotal(20000.0);
        compra.setEstado("COMPLETADA");
        compra.setFechaCreacion(LocalDateTime.now());

        when(servicio.registrarCompra(org.mockito.ArgumentMatchers.any(Compra.class)))
                .thenReturn(compra);

        mockMvc.perform(post("/api/compras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compra)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(20000.0))
                .andExpect(jsonPath("$.estado").value("COMPLETADA"));
    }

    @Test
    @DisplayName("Debería listar todas las compras")
    void deberiaListarCompras() throws Exception {

        Compra compra = new Compra();
        compra.setTotal(15000.0);
        compra.setEstado("COMPLETADA");
        compra.setFechaCreacion(LocalDateTime.now());

        when(servicio.listarTodas())
                .thenReturn(List.of(compra));

        mockMvc.perform(get("/api/compras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].total").value(15000.0))
                .andExpect(jsonPath("$[0].estado").value("COMPLETADA"));
    }

    @Test
    @DisplayName("Debería obtener compra por id")
    void deberiaObtenerCompraPorId() throws Exception {

        Compra compra = new Compra();
        compra.setTotal(30000.0);
        compra.setEstado("COMPLETADA");
        compra.setFechaCreacion(LocalDateTime.now());

        when(servicio.obtenerPorId(1L))
                .thenReturn(Optional.of(compra));

        mockMvc.perform(get("/api/compras/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(30000.0))
                .andExpect(jsonPath("$.estado").value("COMPLETADA"));
    }

    @Test
    @DisplayName("Debería retornar 404 cuando la compra no existe")
    void deberiaRetornar404CuandoCompraNoExiste() throws Exception {

        when(servicio.obtenerPorId(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/compras/99"))
                .andExpect(status().isNotFound());
    }
}