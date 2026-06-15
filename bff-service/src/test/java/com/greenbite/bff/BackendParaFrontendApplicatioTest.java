package com.greenbite.bff;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class BackendParaFrontendApplicationTest {

    @Test
    void contextoCargaCorrectamente() {
        // Si el contexto de Spring inicia correctamente,
        // el test pasa automáticamente.
    }

    @Test
    void deberiaEjecutarMetodoMain() {

        assertDoesNotThrow(() ->
                BackendParaFrontendApplication.main(new String[] {})
        );
    }
}