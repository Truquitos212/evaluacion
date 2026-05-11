package com.greenbite.compras.controlador;

import com.greenbite.compras.modelo.Compra;
import com.greenbite.compras.servicio.CompraServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Expone los endpoints REST para suscripciones/pedidos de GreenBite.
 */
@RestController
@RequestMapping("/api/compras")
public class CompraControlador {

    private final CompraServicio servicio;

    public CompraControlador(CompraServicio servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<Compra> registrar(@RequestBody Compra compra) {
        return ResponseEntity.ok(servicio.registrarCompra(compra));
    }

    @GetMapping
    public ResponseEntity<List<Compra>> listar() {
        return ResponseEntity.ok(servicio.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtenerPorId(@PathVariable Long id) {
        return servicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
