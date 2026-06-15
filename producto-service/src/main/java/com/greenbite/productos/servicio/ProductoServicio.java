package com.greenbite.productos.servicio;

import com.greenbite.productos.modelo.Producto;
import com.greenbite.productos.repositorio.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de productos de GreenBite.
 *
 * Patron Singleton:
 * Spring crea una sola instancia de este servicio para toda la aplicacion.
 * Cualquier componente que lo inyecte recibe la misma instancia.
 *
 * Patron Repository:
 * Delega el acceso a datos en ProductoRepository, manteniendo separada
 * la logica de negocio del acceso a la base de datos.
 */
@Service
public class ProductoServicio {

    private final ProductoRepository repositorio;

    public ProductoServicio(ProductoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Producto> listarTodos() {
        return repositorio.findAll();
    }

    public List<Producto> listarDisponibles() {
        return repositorio.findByStockGreaterThan(0);
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public Producto guardar(Producto producto) {
        return repositorio.save(producto);
    }

    public Optional<Producto> actualizar(Long id, Producto datos) {
        return repositorio.findById(id).map(existente -> {
            existente.setNombre(datos.getNombre());
            existente.setDescripcion(datos.getDescripcion());
            existente.setPrecio(datos.getPrecio());
            existente.setStock(datos.getStock());
            existente.setCategoria(datos.getCategoria());
            return repositorio.save(existente);
        });
    }

    public boolean eliminar(Long id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return repositorio.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return repositorio.findByCategoria(categoria);
    }
}
