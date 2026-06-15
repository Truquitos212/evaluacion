package com.greenbite.compras.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GreenBite Compra Service API")
                        .version("1.0")
                        .description("Microservicio de gestión de compras y suscripciones de cajas orgánicas."))
                .servers(List.of(new Server().url("http://localhost:8082").description("Local Compra Service")));
    }
}
