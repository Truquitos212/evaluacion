package com.greenbite.productos.servicio;

import com.greenbite.productos.modelo.Producto;
import com.greenbite.productos.repositorio.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServicioTest {

    @Mock
    private ProductoRepository repositorio;

    @InjectMocks
    private ProductoServicio servicio;

    @Test
    void deberiaListarTodosLosProductos() {

        when(repositorio.findAll()).thenReturn(List.of(
                new Producto("Caja 1", "Desc", 10000.0, 5, "Semanal"),
                new Producto("Caja 2", "Desc", 20000.0, 10, "Mensual")
        ));

        List<Producto> resultado = servicio.listarTodos();

        assertEquals(2, resultado.size());
        verify(repositorio, times(1)).findAll();
    }

    @Test
    void deberiaListarSoloProductosDisponibles() {

        when(repositorio.findByStockGreaterThan(0)).thenReturn(List.of(
                new Producto("Caja A", "Desc", 10000.0, 3, "Semanal")
        ));

        List<Producto> resultado = servicio.listarDisponibles();

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getStock() > 0);

        verify(repositorio).findByStockGreaterThan(0);
    }

    @Test
    void deberiaObtenerProductoPorId() {

        Producto producto = new Producto("Caja Test", "Desc", 10000.0, 5, "Semanal");

        when(repositorio.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> resultado = servicio.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Caja Test", resultado.get().getNombre());

        verify(repositorio).findById(1L);
    }

    @Test
    void deberiaGuardarProducto() {

        Producto producto = new Producto("Caja Nueva", "Desc", 15000.0, 8, "Mensual");

        when(repositorio.save(producto)).thenReturn(producto);

        Producto resultado = servicio.guardar(producto);

        assertEquals("Caja Nueva", resultado.getNombre());

        verify(repositorio).save(producto);
    }

    @Test
    void deberiaActualizarProductoExistente() {

        Producto existente = new Producto("Caja Old", "Desc", 10000.0, 5, "Semanal");
        existente.setId(1L);

        Producto datos = new Producto("Caja New", "Nuevo Desc", 20000.0, 10, "Mensual");

        when(repositorio.findById(1L)).thenReturn(Optional.of(existente));
        when(repositorio.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Producto> resultado = servicio.actualizar(1L, datos);

        assertTrue(resultado.isPresent());
        assertEquals("Caja New", resultado.get().getNombre());
        assertEquals(20000.0, resultado.get().getPrecio());

        verify(repositorio).findById(1L);
        verify(repositorio).save(existente);
    }

    @Test
    void deberiaEliminarProductoCuandoExiste() {

        when(repositorio.existsById(1L)).thenReturn(true);

        boolean resultado = servicio.eliminar(1L);

        assertTrue(resultado);
        verify(repositorio).deleteById(1L);
    }

    @Test
    void noDeberiaEliminarSiNoExiste() {

        when(repositorio.existsById(1L)).thenReturn(false);

        boolean resultado = servicio.eliminar(1L);

        assertFalse(resultado);
        verify(repositorio, never()).deleteById(anyLong());
    }

    @Test
    void deberiaBuscarPorNombre() {

        when(repositorio.findByNombreContainingIgnoreCase("caja"))
                .thenReturn(List.of(new Producto("Caja Especial", "Desc", 10000.0, 5, "Semanal")));

        List<Producto> resultado = servicio.buscarPorNombre("caja");

        assertEquals(1, resultado.size());
        assertEquals("Caja Especial", resultado.get(0).getNombre());

        verify(repositorio).findByNombreContainingIgnoreCase("caja");
    }

    @Test
    void deberiaBuscarPorCategoria() {

        when(repositorio.findByCategoria("Semanal"))
                .thenReturn(List.of(new Producto("Caja X", "Desc", 10000.0, 5, "Semanal")));

        List<Producto> resultado = servicio.buscarPorCategoria("Semanal");

        assertEquals(1, resultado.size());

        verify(repositorio).findByCategoria("Semanal");
    }
}