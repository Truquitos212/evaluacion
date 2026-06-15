package com.greenbite.compras.servicio;

import com.greenbite.compras.modelo.Compra;
import com.greenbite.compras.modelo.ItemCompra;
import com.greenbite.compras.repositorio.CompraRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompraServicioTest {

    @Mock
    private CompraRepository repositorio;

    @InjectMocks
    private CompraServicio servicio;

    private Compra compra;

    @BeforeEach
    void setUp() {

        ItemCompra item1 = new ItemCompra(1L, 2, 10000.0);
        ItemCompra item2 = new ItemCompra(2L, 1, 5000.0);

        compra = new Compra();
        compra.setItems(List.of(item1, item2));
        compra.setEstado("PENDIENTE");
        compra.setTotal(0.0);
        compra.setFechaCreacion(LocalDateTime.now());
    }

    @Test
    void deberiaRegistrarCompraCalculandoTotal() {

        when(repositorio.save(any(Compra.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Compra resultado = servicio.registrarCompra(compra);

        assertNotNull(resultado);
        assertEquals(25000.0, resultado.getTotal());
        assertEquals("COMPLETADA", resultado.getEstado());

        verify(repositorio).save(compra);
    }

    @Test
    void deberiaListarTodasLasCompras() {

        when(repositorio.findAll()).thenReturn(List.of(compra));

        List<Compra> resultado = servicio.listarTodas();

        assertEquals(1, resultado.size());

        verify(repositorio).findAll();
    }

    @Test
    void deberiaObtenerCompraPorId() {

        when(repositorio.findById(1L))
                .thenReturn(Optional.of(compra));

        Optional<Compra> resultado = servicio.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(compra, resultado.get());

        verify(repositorio).findById(1L);
    }

    @Test
    void deberiaRetornarVacioCuandoNoExisteCompra() {

        when(repositorio.findById(99L))
                .thenReturn(Optional.empty());

        Optional<Compra> resultado = servicio.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());

        verify(repositorio).findById(99L);
    }

    @Test
    void deberiaListarComprasPorEstado() {

        when(repositorio.findByEstado("COMPLETADA"))
                .thenReturn(List.of(compra));

        List<Compra> resultado = servicio.listarPorEstado("COMPLETADA");

        assertEquals(1, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());

        verify(repositorio).findByEstado("COMPLETADA");
    }

    @Test
    void deberiaSobrescribirEstadoYTotalRecibidosDelFrontend() {

        compra.setTotal(999999.0);
        compra.setEstado("CANCELADA");

        when(repositorio.save(any(Compra.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Compra resultado = servicio.registrarCompra(compra);

        assertEquals(25000.0, resultado.getTotal());
        assertEquals("COMPLETADA", resultado.getEstado());

        verify(repositorio).save(compra);
    }
}