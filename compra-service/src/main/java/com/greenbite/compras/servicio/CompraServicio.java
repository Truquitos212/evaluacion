package com.greenbite.compras.servicio;

import com.greenbite.compras.modelo.Compra;
import com.greenbite.compras.modelo.ItemCompra;
import com.greenbite.compras.repositorio.CompraRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de suscripciones de GreenBite.
 *
 * Patron Singleton:
 * Spring gestiona una sola instancia de este servicio (@Service).
 *
 * Patron Repository:
 * Delega el acceso a datos en CompraRepository.
 */
@Service
public class CompraServicio {

    private final CompraRepository repositorio;

    public CompraServicio(CompraRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Registra una nueva suscripcion.
     * Calcula automaticamente el total sumando los subtotales de cada item.
     */
    public Compra registrarCompra(Compra compra) {
        // Calcula el total automaticamente (no se confia en el total que venga del frontend)
        double totalCalculado = compra.getItems().stream()
                .mapToDouble(ItemCompra::getSubtotal)
                .sum();

        compra.setTotal(totalCalculado);
        compra.setEstado("COMPLETADA");

        return repositorio.save(compra);
    }

    public List<Compra> listarTodas() {
        return repositorio.findAll();
    }

    public Optional<Compra> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public List<Compra> listarPorEstado(String estado) {
        return repositorio.findByEstado(estado);
    }
}