package com.greenbite.productos.controlador;

import com.greenbite.productos.modelo.Producto;
import com.greenbite.productos.servicio.ProductoServicio;

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
 * Expone los endpoints REST del catálogo de cajas de GreenBite.
 */
@RestController
@RequestMapping("/api/productos")
@Tag(
    name = "Productos",
    description = "Operaciones relacionadas con el catálogo de cajas orgánicas de GreenBite"
)
public class ProductoControlador {

    private final ProductoServicio servicio;

    public ProductoControlador(ProductoServicio servicio) {
        this.servicio = servicio;
    }

    @Operation(
        summary = "Listar productos",
        description = "Obtiene el catálogo completo de cajas orgánicas."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Listado obtenido correctamente"
    )
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    @Operation(
        summary = "Listar productos disponibles",
        description = "Obtiene únicamente los productos con stock disponible."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Listado de productos disponibles obtenido correctamente"
    )
    @GetMapping("/disponibles")
    public ResponseEntity<List<Producto>> listarDisponibles() {
        return ResponseEntity.ok(servicio.listarDisponibles());
    }

    @Operation(
        summary = "Obtener producto por ID",
        description = "Busca un producto específico utilizando su identificador."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Producto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado"
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(
            @Parameter(
                description = "Identificador único del producto",
                example = "1"
            )
            @PathVariable Long id) {

        return servicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Crear producto",
        description = "Registra un nuevo producto en el catálogo."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Producto creado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Producto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos del producto inválidos"
        )
    })
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        return ResponseEntity.ok(servicio.guardar(producto));
    }

    @Operation(
        summary = "Actualizar producto",
        description = "Modifica la información de un producto existente."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Producto actualizado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Producto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado"
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @Parameter(
                description = "Identificador único del producto",
                example = "1"
            )
            @PathVariable Long id,

            @RequestBody Producto datos) {

        return servicio.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar producto",
        description = "Elimina un producto del catálogo."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Producto eliminado correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado"
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(
                description = "Identificador único del producto",
                example = "1"
            )
            @PathVariable Long id) {

        if (servicio.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}