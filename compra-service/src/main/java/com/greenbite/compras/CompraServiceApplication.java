package com.greenbite.compras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Microservicio de suscripciones/compras de GreenBite.
 * Puerto: 8082
 * Gestiona el registro y seguimiento de pedidos de cajas.
 */
@SpringBootApplication
public class CompraServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompraServiceApplication.class, args);
    }
}
