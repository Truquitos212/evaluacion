package com.greenbite.compras.modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompraTest {

    @Test
    void deberiaCrearCompraConConstructorVacio() {

        Compra compra = new Compra();

        assertNull(compra.getId());
        assertNull(compra.getItems());
        assertNull(compra.getTotal());
        assertNull(compra.getEstado());
        assertNull(compra.getFechaCreacion());
    }

    @Test
    void deberiaAsignarYObtenerValoresConSetters() {

        ItemCompra item = new ItemCompra();

        item.setProductoId(1L);
        item.setCantidad(2);
        item.setPrecioUnitario(4990.0);

        LocalDateTime fecha = LocalDateTime.now();

        Compra compra = new Compra();

        compra.setItems(List.of(item));
        compra.setTotal(9980.0);
        compra.setEstado("COMPLETADA");
        compra.setFechaCreacion(fecha);

        assertEquals(1, compra.getItems().size());
        assertEquals(9980.0, compra.getTotal());
        assertEquals("COMPLETADA", compra.getEstado());
        assertEquals(fecha, compra.getFechaCreacion());

        ItemCompra resultado = compra.getItems().get(0);

        assertEquals(1L, resultado.getProductoId());
        assertEquals(2, resultado.getCantidad());
        assertEquals(4990.0, resultado.getPrecioUnitario());
    }

    @Test
    void deberiaConstruirCompraConBuilder() {

        ItemCompra item = new ItemCompra();

        item.setProductoId(10L);
        item.setCantidad(3);
        item.setPrecioUnitario(3500.0);

        Compra compra = new Compra.Builder()
                .items(List.of(item))
                .total(10500.0)
                .estado("PENDIENTE")
                .build();

        assertNotNull(compra);

        assertEquals(1, compra.getItems().size());
        assertEquals(10500.0, compra.getTotal());
        assertEquals("PENDIENTE", compra.getEstado());

        assertNotNull(compra.getFechaCreacion());
    }

    @Test
    void deberiaUsarEstadoPendientePorDefectoEnBuilder() {

        Compra compra = new Compra.Builder()
                .total(1000.0)
                .build();

        assertEquals("PENDIENTE", compra.getEstado());
        assertEquals(1000.0, compra.getTotal());
        assertNotNull(compra.getFechaCreacion());
    }
}