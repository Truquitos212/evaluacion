package com.greenbite.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Microservicio de productos de GreenBite.
 * Puerto: 8081
 * Gestiona el catalogo de cajas de alimentos organicos.
 */
@SpringBootApplication
public class ProductoServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductoServiceApplication.class, args);
    }
}
