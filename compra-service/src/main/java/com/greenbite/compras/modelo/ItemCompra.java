package com.greenbite.compras.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Schema(
    name = "ItemCompra",
    description = "Representa un producto incluido dentro de una compra"
)
@Entity
@Table(name = "items_compra")
public class ItemCompra {

    @Schema(
        description = "Identificador único del ítem de compra",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(
        description = "Identificador del producto comprado",
        example = "3"
    )
    @Column(nullable = false)
    private Long productoId;

    @Schema(
        description = "Cantidad de unidades compradas",
        example = "2",
        minimum = "1"
    )
    @Column(nullable = false)
    private Integer cantidad;

    @Schema(
        description = "Precio unitario del producto al momento de la compra",
        example = "12490.0",
        minimum = "0"
    )
    @Column(nullable = false)
    private Double precioUnitario;

    public ItemCompra() {}

    public ItemCompra(Long productoId, Integer cantidad, Double precioUnitario) {
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Long getId() { return id; }

    public Long getProductoId() { return productoId; }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() { return cantidad; }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Schema(
        description = "Subtotal calculado automáticamente según la cantidad y el precio unitario",
        example = "24980.0",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    public Double getSubtotal() {
        return precioUnitario * cantidad;
    }
}