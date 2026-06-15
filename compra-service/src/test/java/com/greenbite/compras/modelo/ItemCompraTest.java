package com.greenbite.compras.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemCompraTest {

    @Test
    void deberiaCrearItemConConstructorVacio() {

        ItemCompra item = new ItemCompra();

        assertNotNull(item);
        assertNull(item.getId());
        assertNull(item.getProductoId());
        assertNull(item.getCantidad());
        assertNull(item.getPrecioUnitario());
    }

    @Test
    void deberiaCrearItemConConstructorCompleto() {

        ItemCompra item = new ItemCompra(1L, 2, 12500.0);

        assertEquals(1L, item.getProductoId());
        assertEquals(2, item.getCantidad());
        assertEquals(12500.0, item.getPrecioUnitario());
    }

    @Test
    void deberiaModificarCamposConSetters() {

        ItemCompra item = new ItemCompra();

        item.setProductoId(5L);
        item.setCantidad(3);
        item.setPrecioUnitario(7990.0);

        assertEquals(5L, item.getProductoId());
        assertEquals(3, item.getCantidad());
        assertEquals(7990.0, item.getPrecioUnitario());
    }

    @Test
    void deberiaCalcularSubtotalCorrectamente() {

        ItemCompra item = new ItemCompra(10L, 4, 2500.0);

        assertEquals(10000.0, item.getSubtotal());
    }

    @Test
    void deberiaCalcularSubtotalConDecimales() {

        ItemCompra item = new ItemCompra(2L, 3, 1499.99);

        assertEquals(4499.97, item.getSubtotal(), 0.001);
    }
}