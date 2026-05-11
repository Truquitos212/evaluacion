package com.greenbite.productos;

import com.greenbite.productos.modelo.Producto;
import com.greenbite.productos.repositorio.ProductoRepository;
import com.greenbite.productos.servicio.ProductoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del microservicio de productos de GreenBite.
 * Verifican que la logica de negocio del catalogo de cajas funciona correctamente.
 */
@ExtendWith(MockitoExtension.class)
class ProductoServicioTest {

    @Mock
    private ProductoRepository repositorio;

    @InjectMocks
    private ProductoServicio servicio;

    private Producto cajaPrueba;

    @BeforeEach
    void setUp() {
        cajaPrueba = new Producto(
            "Caja Verduras de Temporada",
            "Seleccion semanal de verduras organicas locales",
            12990.0, 20, "Verduras"
        );
    }

    @Test
    void guardarCaja_deberiaRetornarCajaGuardada() {
        when(repositorio.save(cajaPrueba)).thenReturn(cajaPrueba);
        Producto resultado = servicio.guardar(cajaPrueba);
        assertEquals("Caja Verduras de Temporada", resultado.getNombre());
        verify(repositorio, times(1)).save(cajaPrueba);
    }

    @Test
    void listarTodas_deberiaRetornarListaCompleta() {
        Producto caja2 = new Producto("Caja Frutas Organicas", "Mix de frutas", 10990.0, 15, "Frutas");
        when(repositorio.findAll()).thenReturn(List.of(cajaPrueba, caja2));
        List<Producto> resultado = servicio.listarTodos();
        assertEquals(2, resultado.size());
    }

    @Test
    void eliminarCaja_siExiste_deberiaRetornarTrue() {
        when(repositorio.existsById(1L)).thenReturn(true);
        boolean resultado = servicio.eliminar(1L);
        assertTrue(resultado);
        verify(repositorio, times(1)).deleteById(1L);
    }

    @Test
    void eliminarCaja_siNoExiste_deberiaRetornarFalse() {
        when(repositorio.existsById(99L)).thenReturn(false);
        boolean resultado = servicio.eliminar(99L);
        assertFalse(resultado);
    }

    @Test
    void obtenerPorId_siExiste_deberiaRetornarCaja() {
        when(repositorio.findById(1L)).thenReturn(Optional.of(cajaPrueba));
        Optional<Producto> resultado = servicio.obtenerPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Verduras", resultado.get().getCategoria());
    }
}
