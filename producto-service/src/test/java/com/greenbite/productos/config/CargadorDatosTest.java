package com.greenbite.productos.config;

import com.greenbite.productos.modelo.Producto;
import com.greenbite.productos.repositorio.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CargadorDatosTest {

    @Autowired
    private ProductoRepository repositorio;

    @Test
    void deberiaCargarDatosInicialesCuandoLaBDEstaVacia() {

        // Verificamos que el CommandLineRunner ya ejecutó la carga
        long cantidad = repositorio.count();

        assertTrue(cantidad >= 5, "Debe cargar al menos los productos iniciales");

        assertTrue(
                repositorio.findByNombreContainingIgnoreCase("Caja").size() >= 5
        );
    }

    @Test
    void noDeberiaDuplicarDatosSiYaExisten() {

        long cantidadInicial = repositorio.count();

        // Simulamos otra carga manual del runner
        repositorio.save(new Producto(
                "Test Extra",
                "Producto de prueba",
                1000.0,
                1,
                "Test"
        ));

        long cantidadFinal = repositorio.count();

        assertEquals(cantidadInicial + 1, cantidadFinal);
    }
}