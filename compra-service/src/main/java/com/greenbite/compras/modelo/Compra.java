package com.greenbite.compras.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Representa una suscripción/pedido de cajas en GreenBite.
 *
 * Patrón Builder:
 * El constructor privado obliga a usar el Builder para crear instancias,
 * lo que hace el código más legible y evita errores al pasar parámetros.
 */
@Schema(
    name = "Compra",
    description = "Representa una compra o suscripción realizada por un cliente en GreenBite"
)
@Entity
@Table(name = "compras")
public class Compra {

    @Schema(
        description = "Identificador único de la compra",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(
        description = "Listado de productos incluidos en la compra"
    )
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "compra_id")
    private List<ItemCompra> items;

    @Schema(
        description = "Monto total de la compra",
        example = "24990.0",
        minimum = "0"
    )
    @Column(nullable = false)
    private Double total;

    @Schema(
        description = "Estado actual de la compra",
        example = "COMPLETADA",
        allowableValues = {
            "PENDIENTE",
            "COMPLETADA",
            "CANCELADA"
        }
    )
    @Column(nullable = false)
    private String estado;

    @Schema(
        description = "Fecha y hora de creación de la compra",
        example = "2026-06-15T11:30:00",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    public Compra() {}

    // Patrón Builder
    private Compra(Builder builder) {
        this.items = builder.items;
        this.total = builder.total;
        this.estado = builder.estado;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public List<ItemCompra> getItems() { return items; }

    public void setItems(List<ItemCompra> items) { this.items = items; }

    public Double getTotal() { return total; }

    public void setTotal(Double total) { this.total = total; }

    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public static class Builder {

        private List<ItemCompra> items;
        private Double total;
        private String estado = "PENDIENTE";

        public Builder items(List<ItemCompra> items) {
            this.items = items;
            return this;
        }

        public Builder total(Double total) {
            this.total = total;
            return this;
        }

        public Builder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public Compra build() {
            return new Compra(this);
        }
    }
}