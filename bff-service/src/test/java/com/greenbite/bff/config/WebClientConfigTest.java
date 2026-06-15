package com.greenbite.bff.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

class WebClientConfigTest {

    private final WebClientConfig config = new WebClientConfig();

    @Test
    void deberiaCrearClienteProductos() {

        WebClient.Builder builder = WebClient.builder();

        WebClient cliente = config.clienteProductos(
                builder,
                "http://localhost:8081"
        );

        assertNotNull(cliente);
    }

    @Test
    void deberiaCrearClienteCompras() {

        WebClient.Builder builder = WebClient.builder();

        WebClient cliente = config.clienteCompras(
                builder,
                "http://localhost:8082"
        );

        assertNotNull(cliente);
    }
}