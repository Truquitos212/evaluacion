package com.greenbite.productos.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

/**
 * Representa una caja de alimentos orgánicos en GreenBite.
 * Patrón Builder: se puede construir mediante el patrón builder usando Lombok (@Builder).
 */
@Schema(
    name = "Producto",
    description = "Representa una caja orgánica disponible en el catálogo de GreenBite"
)
@Entity
@Table(name = "productos")
public class Producto {

    @Schema(
        description = "Identificador único del producto",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(
        description = "Nombre de la caja orgánica",
        example = "Caja Familiar Orgánica"
    )
    @Column(nullable = false)
    private String nombre;

    @Schema(
        description = "Descripción detallada del producto",
        example = "Selección semanal de frutas y verduras orgánicas para una familia de cuatro personas"
    )
    @Column(length = 500)
    private String descripcion;

    @Schema(
        description = "Precio de venta del producto",
        example = "24990.0",
        minimum = "0"
    )
    @Column(nullable = false)
    private Double precio;

    @Schema(
        description = "Cantidad disponible en inventario",
        example = "15",
        minimum = "0"
    )
    @Column(nullable = false)
    private Integer stock;

    @Schema(
        description = "Categoría del producto",
        example = "Suscripción mensual"
    )
    private String categoria;

    public Producto() {}

    public Producto(
            String nombre,
            String descripcion,
            Double precio,
            Integer stock,
            String categoria) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }

    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getStock() { return stock; }

    public void setStock(Integer stock) { this.stock = stock; }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }
}