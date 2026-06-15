package com.greenbite.bff.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompraDTOTest {

    @Test
    void deberiaAsignarYObtenerValores() {

        CompraDTO.ItemCompraDTO item = new CompraDTO.ItemCompraDTO();

        item.setProductoId(10L);
        item.setCantidad(2);
        item.setPrecioUnitario(4990.0);

        CompraDTO compra = new CompraDTO();

        compra.setId(1L);
        compra.setItems(List.of(item));
        compra.setTotal(9980.0);
        compra.setEstado("COMPLETADA");

        assertEquals(1L, compra.getId());
        assertEquals(1, compra.getItems().size());
        assertEquals(9980.0, compra.getTotal());
        assertEquals("COMPLETADA", compra.getEstado());

        CompraDTO.ItemCompraDTO resultado = compra.getItems().get(0);

        assertEquals(10L, resultado.getProductoId());
        assertEquals(2, resultado.getCantidad());
        assertEquals(4990.0, resultado.getPrecioUnitario());
    }

    @Test
    void deberiaCrearCompraVacia() {

        CompraDTO compra = new CompraDTO();

        assertNull(compra.getId());
        assertNull(compra.getItems());
        assertNull(compra.getTotal());
        assertNull(compra.getEstado());
    }

    @Test
    void deberiaCrearItemCompraVacio() {

        CompraDTO.ItemCompraDTO item = new CompraDTO.ItemCompraDTO();

        assertNull(item.getProductoId());
        assertNull(item.getCantidad());
        assertNull(item.getPrecioUnitario());
    }
}