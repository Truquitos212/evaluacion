package com.greenbite.productos.repositorio;

import com.greenbite.productos.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Patron Repository:
 * Abstrae el acceso a la base de datos para el catalogo de cajas.
 * El servicio no necesita saber como se almacenan los datos.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByStockGreaterThan(Integer stock);
}
