package com.greenbite.bff.service;

import com.greenbite.bff.dto.CompraDTO;
import com.greenbite.bff.dto.ProductoDTO;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Arrays;
import java.util.List;

/**
 * Patron Facade:
 * Oculta la complejidad de comunicarse con los microservicios de GreenBite.
 * El frontend solo habla con este servicio, sin saber que existen dos
 * microservicios separados (producto-service y compra-service).
 *
 * Patron Singleton:
 * Spring gestiona esta clase como singleton (@Service), lo que garantiza
 * que existe una sola instancia compartida en toda la aplicacion.
 */
@Service
public class BackendParaFrontendService {

    private final WebClient clienteProductos;
    private final WebClient clienteCompras;

    public BackendParaFrontendService(

            @Qualifier("clienteProductos")
            WebClient clienteProductos,

            @Qualifier("clienteCompras")
            WebClient clienteCompras) {

        this.clienteProductos = clienteProductos;
        this.clienteCompras = clienteCompras;
    }

    /**
     * Obtiene todas las cajas disponibles desde el microservicio de productos.
     * El Backend Para Frontend agrega y entrega los datos listos para el frontend.
     */
    public List<ProductoDTO> obtenerProductos() {
        ProductoDTO[] productos = clienteProductos.get()
                .uri("/api/productos")
                .retrieve()
                .bodyToMono(ProductoDTO[].class)
                .block();
        return productos != null ? Arrays.asList(productos) : List.of();
    }

    /**
     * Obtiene una caja especifica por ID.
     */
    public ProductoDTO obtenerProductoPorId(Long id) {
        return clienteProductos.get()
                .uri("/api/productos/{id}", id)
                .retrieve()
                .bodyToMono(ProductoDTO.class)
                .block();
    }

    /**
     * Registra una compra/suscripcion enviandola al microservicio de compras.
     */
    public CompraDTO procesarCompra(CompraDTO compra) {
        return clienteCompras.post()
                .uri("/api/compras")
                .bodyValue(compra)
                .retrieve()
                .bodyToMono(CompraDTO.class)
                .block();
    }
}
