package com.greenbite.productos.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    void deberiaCrearProductoConConstructorVacio() {

        Producto producto = new Producto();

        assertNotNull(producto);
        assertNull(producto.getId());
        assertNull(producto.getNombre());
        assertNull(producto.getDescripcion());
        assertNull(producto.getPrecio());
        assertNull(producto.getStock());
        assertNull(producto.getCategoria());
    }

    @Test
    void deberiaCrearProductoConConstructorCompleto() {

        Producto producto = new Producto(
                "Caja Familiar Orgánica",
                "Frutas y verduras para cuatro personas",
                24990.0,
                15,
                "Suscripción mensual"
        );

        assertEquals("Caja Familiar Orgánica", producto.getNombre());
        assertEquals("Frutas y verduras para cuatro personas", producto.getDescripcion());
        assertEquals(24990.0, producto.getPrecio());
        assertEquals(15, producto.getStock());
        assertEquals("Suscripción mensual", producto.getCategoria());
    }

    @Test
    void deberiaModificarCamposConSetters() {

        Producto producto = new Producto();

        producto.setId(1L);
        producto.setNombre("Caja Premium");
        producto.setDescripcion("Selección premium de productos orgánicos");
        producto.setPrecio(34990.0);
        producto.setStock(10);
        producto.setCategoria("Suscripción semanal");

        assertEquals(1L, producto.getId());
        assertEquals("Caja Premium", producto.getNombre());
        assertEquals("Selección premium de productos orgánicos", producto.getDescripcion());
        assertEquals(34990.0, producto.getPrecio());
        assertEquals(10, producto.getStock());
        assertEquals("Suscripción semanal", producto.getCategoria());
    }

    @Test
    void deberiaActualizarValoresExistentes() {

        Producto producto = new Producto(
                "Caja Básica",
                "Descripción inicial",
                19990.0,
                20,
                "Semanal"
        );

        producto.setNombre("Caja Familiar");
        producto.setPrecio(22990.0);
        producto.setStock(12);

        assertEquals("Caja Familiar", producto.getNombre());
        assertEquals(22990.0, producto.getPrecio());
        assertEquals(12, producto.getStock());
    }
}