package com.greenbite.compras;

import com.greenbite.compras.modelo.Compra;
import com.greenbite.compras.modelo.ItemCompra;
import com.greenbite.compras.repositorio.CompraRepository;
import com.greenbite.compras.servicio.CompraServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del microservicio de suscripciones de GreenBite.
 */
@ExtendWith(MockitoExtension.class)
class CompraServicioTest {

    @Mock
    private CompraRepository repositorio;

    @InjectMocks
    private CompraServicio servicio;

    @Test
    void registrarCompra_deberiaCalcularTotalCorrectamente() {
        // Caja Verduras ($12.990) x2 + Caja Frutas ($10.990) x1 = $36.970
        List<ItemCompra> items = List.of(
            new ItemCompra(1L, 2, 12990.0),
            new ItemCompra(2L, 1, 10990.0)
        );

        Compra compra = new Compra.Builder()
            .items(items)
            .total(0.0)
            .build();

        when(repositorio.save(any(Compra.class))).thenAnswer(inv -> inv.getArgument(0));

        Compra resultado = servicio.registrarCompra(compra);

        assertEquals(36970.0, resultado.getTotal());
        assertEquals("COMPLETADA", resultado.getEstado());
    }

    @Test
    void registrarCompra_deberiaIgnorarTotalDelFrontend() {
        // El frontend manda total incorrecto, el servicio lo recalcula
        List<ItemCompra> items = List.of(
            new ItemCompra(3L, 1, 19990.0)
        );

        Compra compra = new Compra.Builder()
            .items(items)
            .total(999.0) // total incorrecto del frontend
            .build();

        when(repositorio.save(any(Compra.class))).thenAnswer(inv -> inv.getArgument(0));

        Compra resultado = servicio.registrarCompra(compra);

        assertEquals(19990.0, resultado.getTotal());
    }

    @Test
    void registrarCompra_deberiaMarcarseComoCompletada() {
        List<ItemCompra> items = List.of(new ItemCompra(1L, 1, 6990.0));
        Compra compra = new Compra.Builder().items(items).total(0.0).build();
        when(repositorio.save(any(Compra.class))).thenAnswer(inv -> inv.getArgument(0));

        Compra resultado = servicio.registrarCompra(compra);

        assertEquals("COMPLETADA", resultado.getEstado());
        verify(repositorio, times(1)).save(compra);
    }
}
