package com.greenbite.compras.controlador;

import com.greenbite.compras.modelo.Compra;
import com.greenbite.compras.servicio.CompraServicio;

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
 * Expone los endpoints REST para suscripciones/pedidos de GreenBite.
 */
@RestController
@RequestMapping("/api/compras")
@Tag(
    name = "Compras",
    description = "Operaciones relacionadas con las compras y suscripciones de GreenBite"
)
public class CompraControlador {

    private final CompraServicio servicio;

    public CompraControlador(CompraServicio servicio) {
        this.servicio = servicio;
    }

    @Operation(
        summary = "Registrar una compra",
        description = "Crea una nueva compra, calcula el total y registra la suscripción."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Compra registrada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Compra.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de compra inválidos"
        )
    })
    @PostMapping
    public ResponseEntity<Compra> registrar(@RequestBody Compra compra) {
        return ResponseEntity.ok(servicio.registrarCompra(compra));
    }

    @Operation(
        summary = "Listar compras",
        description = "Obtiene el historial completo de compras registradas."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente"
        )
    })
    @GetMapping
    public ResponseEntity<List<Compra>> listar() {
        return ResponseEntity.ok(servicio.listarTodas());
    }

    @Operation(
        summary = "Obtener compra por ID",
        description = "Busca una compra específica mediante su identificador."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Compra encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Compra.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Compra no encontrada"
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtenerPorId(
            @Parameter(
                description = "Identificador único de la compra",
                example = "1"
            )
            @PathVariable Long id) {

        return servicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}