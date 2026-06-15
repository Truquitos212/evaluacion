package com.greenbite.bff.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("clienteProductos")
    public WebClient clienteProductos(
            WebClient.Builder builder,
            @Value("${microservicio.producto.url:http://localhost:8081}")
            String urlProductos) {

        return builder
                .baseUrl(urlProductos)
                .build();
    }

    @Bean
    @Qualifier("clienteCompras")
    public WebClient clienteCompras(
            WebClient.Builder builder,
            @Value("${microservicio.compra.url:http://localhost:8082}")
            String urlCompras) {

        return builder
                .baseUrl(urlCompras)
                .build();
    }
}