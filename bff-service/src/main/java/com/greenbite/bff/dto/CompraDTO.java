package com.greenbite.bff.dto;

import java.util.List;

/**
 * DTO para transferir datos de una suscripcion/compra entre servicios.
 */
public class CompraDTO {
    private Long id;
    private List<ItemCompraDTO> items;
    private Double total;
    private String estado;

    public CompraDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public List<ItemCompraDTO> getItems() { return items; }
    public void setItems(List<ItemCompraDTO> items) { this.items = items; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public static class ItemCompraDTO {
        private Long productoId;
        private Integer cantidad;
        private Double precioUnitario;

        public ItemCompraDTO() {}

        public Long getProductoId() { return productoId; }
        public void setProductoId(Long productoId) { this.productoId = productoId; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
        public Double getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
    }
}
