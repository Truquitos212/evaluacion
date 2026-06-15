package com.greenbite.productos.controlador;

import com.greenbite.productos.modelo.Producto;
import com.greenbite.productos.servicio.ProductoServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoControlador.class)
class ProductoControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoServicio servicio;

    @Test
    void deberiaListarProductos() throws Exception {

        when(servicio.listarTodos()).thenReturn(List.of(
                new Producto("Caja 1", "Desc", 10000.0, 5, "Semanal")
        ));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Caja 1"));

        verify(servicio).listarTodos();
    }

    @Test
    void deberiaListarDisponibles() throws Exception {

        when(servicio.listarDisponibles()).thenReturn(List.of(
                new Producto("Caja A", "Desc", 10000.0, 3, "Semanal")
        ));

        mockMvc.perform(get("/api/productos/disponibles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(servicio).listarDisponibles();
    }

    @Test
    void deberiaObtenerProductoPorId() throws Exception {

        Producto producto = new Producto("Caja X", "Desc", 10000.0, 5, "Semanal");

        when(servicio.obtenerPorId(1L)).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Caja X"));

        verify(servicio).obtenerPorId(1L);
    }

    @Test
    void deberiaRetornar404SiNoExisteProducto() throws Exception {

        when(servicio.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isNotFound());

        verify(servicio).obtenerPorId(1L);
    }

    @Test
    void deberiaCrearProducto() throws Exception {

        Producto producto = new Producto("Caja Nueva", "Desc", 15000.0, 10, "Mensual");

        when(servicio.guardar(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "Caja Nueva",
                                  "descripcion": "Desc",
                                  "precio": 15000.0,
                                  "stock": 10,
                                  "categoria": "Mensual"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Caja Nueva"));

        verify(servicio).guardar(any(Producto.class));
    }

    @Test
    void deberiaActualizarProducto() throws Exception {

        Producto actualizado = new Producto("Caja Editada", "Desc", 20000.0, 8, "Semanal");

        when(servicio.actualizar(eq(1L), any(Producto.class)))
                .thenReturn(Optional.of(actualizado));

        mockMvc.perform(put("/api/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "Caja Editada",
                                  "descripcion": "Desc",
                                  "precio": 20000.0,
                                  "stock": 8,
                                  "categoria": "Semanal"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Caja Editada"));

        verify(servicio).actualizar(eq(1L), any(Producto.class));
    }

    @Test
    void deberiaRetornar404EnActualizarSiNoExiste() throws Exception {

        when(servicio.actualizar(eq(1L), any(Producto.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "X",
                                  "descripcion": "Y",
                                  "precio": 1000.0,
                                  "stock": 1,
                                  "categoria": "Z"
                                }
                                """))
                .andExpect(status().isNotFound());

        verify(servicio).actualizar(eq(1L), any(Producto.class));
    }

    @Test
    void deberiaEliminarProducto() throws Exception {

        when(servicio.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNoContent());

        verify(servicio).eliminar(1L);
    }

    @Test
    void deberiaRetornar404EnEliminar() throws Exception {

        when(servicio.eliminar(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNotFound());

        verify(servicio).eliminar(1L);
    }
}