package cl.nexbyte.api.dto;

import java.math.BigDecimal;

public class PedidoItemView {
  private Long idProducto;
  private String sku;
  private String nombre;
  private Integer cantidad;
  private BigDecimal precioUnitario;
  private BigDecimal subtotal;

  public Long getIdProducto() { return idProducto; }
  public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }
  public String getSku() { return sku; }
  public void setSku(String sku) { this.sku = sku; }
  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }
  public Integer getCantidad() { return cantidad; }
  public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
  public BigDecimal getPrecioUnitario() { return precioUnitario; }
  public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
  public BigDecimal getSubtotal() { return subtotal; }
  public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
