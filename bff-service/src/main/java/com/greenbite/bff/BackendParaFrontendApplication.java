package com.greenbite.bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Backend Para Frontend de GreenBite.
 * Puerto: 8080
 * Actua como puerta de entrada unica para el frontend React.
 */
@SpringBootApplication
public class BackendParaFrontendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendParaFrontendApplication.class, args);
    }
}
