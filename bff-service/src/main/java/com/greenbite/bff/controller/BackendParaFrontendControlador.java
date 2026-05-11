package com.greenbite.bff.controller;

import com.greenbite.bff.dto.CompraDTO;
import com.greenbite.bff.dto.ProductoDTO;
import com.greenbite.bff.service.BackendParaFrontendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador del Backend Para Frontend de GreenBite.
 *
 * Expone una API unificada al frontend React.
 * Delega toda la logica al BackendParaFrontendService (Patron Facade).
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BackendParaFrontendControlador {

    private final BackendParaFrontendService servicio;

    public BackendParaFrontendControlador(BackendParaFrontendService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> listarCajas() {
        return ResponseEntity.ok(servicio.obtenerProductos());
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> obtenerCaja(@PathVariable Long id) {
        ProductoDTO producto = servicio.obtenerProductoPorId(id);
        if (producto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(producto);
    }

    @PostMapping("/compras")
    public ResponseEntity<CompraDTO> registrarSuscripcion(@RequestBody CompraDTO compra) {
        CompraDTO resultado = servicio.procesarCompra(compra);
        return ResponseEntity.ok(resultado);
    }
}
