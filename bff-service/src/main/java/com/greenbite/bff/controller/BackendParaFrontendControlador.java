package com.greenbite.bff.controller;

import com.greenbite.bff.dto.CompraDTO;
import com.greenbite.bff.dto.ProductoDTO;
import com.greenbite.bff.service.BackendParaFrontendService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador del Backend Para Frontend de GreenBite.
 *
 * Expone una API unificada al frontend React.
 * Delega toda la lógica al BackendParaFrontendService (Patrón Facade).
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(
    name = "BFF",
    description = "API unificada que centraliza el acceso a los microservicios de productos y compras"
)
public class BackendParaFrontendControlador {

    private final BackendParaFrontendService servicio;

    public BackendParaFrontendControlador(BackendParaFrontendService servicio) {
        this.servicio = servicio;
    }

    @Operation(
        summary = "Listar productos",
        description = "Obtiene el catálogo completo de productos desde el microservicio de productos."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Listado obtenido correctamente"
    )
    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> listarCajas() {
        return ResponseEntity.ok(servicio.obtenerProductos());
    }

    @Operation(
        summary = "Obtener producto por ID",
        description = "Obtiene un producto específico desde el microservicio de productos."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductoDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado"
        )
    })
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> obtenerCaja(
            @Parameter(
                description = "Identificador único del producto",
                example = "1"
            )
            @PathVariable Long id) {

        ProductoDTO producto = servicio.obtenerProductoPorId(id);

        if (producto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(producto);
    }

    @Operation(
        summary = "Registrar compra",
        description = "Envía una compra al microservicio de compras para su procesamiento."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Compra registrada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CompraDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de compra inválidos"
        ),
        @ApiResponse(
            responseCode = "503",
            description = "Microservicio de compras no disponible"
        )
    })
    @PostMapping("/compras")
    public ResponseEntity<CompraDTO> registrarSuscripcion(
            @RequestBody CompraDTO compra) {

        CompraDTO resultado = servicio.procesarCompra(compra);

        return ResponseEntity.ok(resultado);
    }
}