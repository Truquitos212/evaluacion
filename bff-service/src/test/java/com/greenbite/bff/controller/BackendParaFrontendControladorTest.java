package com.greenbite.bff.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenbite.bff.dto.CompraDTO;
import com.greenbite.bff.dto.ProductoDTO;
import com.greenbite.bff.service.BackendParaFrontendService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BackendParaFrontendControlador.class)
class BackendParaFrontendControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BackendParaFrontendService servicio;

    @Test
    void deberiaListarProductos() throws Exception {

        ProductoDTO producto = new ProductoDTO();
        producto.setId(1L);
        producto.setNombre("Caja Familiar");

        when(servicio.obtenerProductos())
                .thenReturn(List.of(producto));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre")
                        .value("Caja Familiar"));
    }

    @Test
    void deberiaObtenerProductoPorId() throws Exception {

        ProductoDTO producto = new ProductoDTO();
        producto.setId(1L);
        producto.setNombre("Caja Familiar");

        when(servicio.obtenerProductoPorId(1L))
                .thenReturn(producto);

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre")
                        .value("Caja Familiar"));
    }

    @Test
    void deberiaRetornar404CuandoProductoNoExiste() throws Exception {

        when(servicio.obtenerProductoPorId(99L))
                .thenReturn(null);

        mockMvc.perform(get("/api/productos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deberiaRegistrarCompra() throws Exception {

        CompraDTO compra = new CompraDTO();
        compra.setId(1L);
        compra.setEstado("PENDIENTE");

        CompraDTO respuesta = new CompraDTO();
        respuesta.setId(1L);
        respuesta.setEstado("COMPLETADA");

        when(servicio.procesarCompra(org.mockito.ArgumentMatchers.any(CompraDTO.class)))
                .thenReturn(respuesta);

        mockMvc.perform(post("/api/compras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compra)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado")
                        .value("COMPLETADA"));
    }
}