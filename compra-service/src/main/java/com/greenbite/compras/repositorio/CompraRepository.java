package com.greenbite.compras.repositorio;

import com.greenbite.compras.modelo.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Patron Repository:
 * Abstrae el acceso a la base de datos para las suscripciones de GreenBite.
 */
@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByEstado(String estado);
}
