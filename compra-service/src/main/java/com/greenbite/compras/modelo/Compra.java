package com.greenbite.compras.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Representa una suscripcion/pedido de cajas en GreenBite.
 *
 * Patron Builder:
 * El constructor privado obliga a usar el Builder para crear instancias,
 * lo que hace el codigo mas legible y evita errores al pasar parametros.
 */
@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "compra_id")
    private List<ItemCompra> items;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    public Compra() {}

    // Patron Builder
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
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

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
