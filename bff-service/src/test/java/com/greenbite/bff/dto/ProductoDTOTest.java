package com.greenbite.bff.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoDTOTest {

    @Test
    void deberiaAsignarYObtenerValores() {

        ProductoDTO producto = new ProductoDTO();

        producto.setId(1L);
        producto.setNombre("Caja Familiar");
        producto.setDescripcion("Caja con productos frescos");
        producto.setPrecio(19990.0);
        producto.setStock(15);
        producto.setCategoria("Verduras");

        assertEquals(1L, producto.getId());
        assertEquals("Caja Familiar", producto.getNombre());
        assertEquals("Caja con productos frescos", producto.getDescripcion());
        assertEquals(19990.0, producto.getPrecio());
        assertEquals(15, producto.getStock());
        assertEquals("Verduras", producto.getCategoria());
    }

    @Test
    void deberiaCrearProductoVacio() {

        ProductoDTO producto = new ProductoDTO();

        assertNull(producto.getId());
        assertNull(producto.getNombre());
        assertNull(producto.getDescripcion());
        assertNull(producto.getPrecio());
        assertNull(producto.getStock());
        assertNull(producto.getCategoria());
    }
}