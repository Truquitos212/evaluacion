package com.greenbite.productos.config;

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
                        .title("GreenBite Producto Service API")
                        .version("1.0")
                        .description("Microservicio de catálogo de cajas orgánicas que expone operaciones CRUD de productos."))
                .servers(List.of(new Server().url("http://localhost:8081").description("Local Producto Service")));
    }
}
