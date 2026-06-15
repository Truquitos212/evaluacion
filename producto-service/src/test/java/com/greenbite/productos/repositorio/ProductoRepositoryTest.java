package com.greenbite.productos.repositorio;

import com.greenbite.productos.modelo.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void deberiaGuardarYBuscarProductoPorNombre() {

        Producto producto = new Producto(
                "Caja Familiar Orgánica",
                "Frutas y verduras",
                24990.0,
                15,
                "Suscripción mensual"
        );

        productoRepository.save(producto);

        List<Producto> resultados =
                productoRepository.findByNombreContainingIgnoreCase("familiar");

        assertFalse(resultados.isEmpty());
        assertEquals("Caja Familiar Orgánica", resultados.get(0).getNombre());
    }

    @Test
    void deberiaBuscarPorCategoria() {

        Producto p1 = new Producto("Caja 1", "Desc", 10000.0, 5, "Semanal");
        Producto p2 = new Producto("Caja 2", "Desc", 20000.0, 5, "Mensual");

        productoRepository.save(p1);
        productoRepository.save(p2);

        List<Producto> resultados =
                productoRepository.findByCategoria("Semanal");

        assertEquals(1, resultados.size());
        assertEquals("Caja 1", resultados.get(0).getNombre());
    }

    @Test
    void deberiaEncontrarProductosConStockMayorA() {

        Producto p1 = new Producto("Caja A", "Desc", 10000.0, 2, "Semanal");
        Producto p2 = new Producto("Caja B", "Desc", 10000.0, 20, "Semanal");

        productoRepository.save(p1);
        productoRepository.save(p2);

        List<Producto> resultados =
                productoRepository.findByStockGreaterThan(10);

        assertEquals(1, resultados.size());
        assertEquals("Caja B", resultados.get(0).getNombre());
    }
}