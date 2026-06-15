package com.greenbite.compras.repositorio;

import com.greenbite.compras.modelo.Compra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CompraRepositoryTest {

    @Autowired
    private CompraRepository repository;

    @Test
    @DisplayName("Debería guardar una compra")
    void deberiaGuardarCompra() {

        Compra compra = new Compra();
        compra.setTotal(24990.0);
        compra.setEstado("PENDIENTE");
        compra.setFechaCreacion(LocalDateTime.now());

        Compra resultado = repository.save(compra);

        assertNotNull(resultado.getId());
        assertEquals(24990.0, resultado.getTotal());
        assertEquals("PENDIENTE", resultado.getEstado());
    }

    @Test
    @DisplayName("Debería buscar compra por estado")
    void deberiaBuscarPorEstado() {

        Compra pendiente = new Compra();
        pendiente.setTotal(10000.0);
        pendiente.setEstado("PENDIENTE");
        pendiente.setFechaCreacion(LocalDateTime.now());

        Compra completada = new Compra();
        completada.setTotal(20000.0);
        completada.setEstado("COMPLETADA");
        completada.setFechaCreacion(LocalDateTime.now());

        repository.save(pendiente);
        repository.save(completada);

        List<Compra> resultado = repository.findByEstado("PENDIENTE");

        assertEquals(1, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no existan compras con el estado indicado")
    void deberiaRetornarListaVacia() {

        List<Compra> resultado = repository.findByEstado("CANCELADA");

        assertTrue(resultado.isEmpty());
    }
}