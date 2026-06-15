package com.greenbite.compras;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class CompraServiceApplicationTest {

    @Test
    void contextoCargaCorrectamente() {
        // El test pasa si Spring levanta el contexto.
    }

    @Test
    void deberiaEjecutarMetodoMain() {

        assertDoesNotThrow(() ->
                CompraServiceApplication.main(new String[]{})
        );
    }
}