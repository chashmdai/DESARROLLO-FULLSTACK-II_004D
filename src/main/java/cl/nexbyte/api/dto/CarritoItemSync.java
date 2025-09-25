package cl.nexbyte.api.dto;

import java.math.BigDecimal;

public class CarritoItemSync {
  private Long idProducto;
  private String sku;
  private String nombre;
  private String imagenUrl;
  private BigDecimal precioUnitario;
  private int cantidadSolicitada;
  private int cantidadAceptada;
  private BigDecimal subtotal;

  public Long getIdProducto() { return idProducto; }
  public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }
  public String getSku() { return sku; }
  public void setSku(String sku) { this.sku = sku; }
  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }
  public String getImagenUrl() { return imagenUrl; }
  public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
  public BigDecimal getPrecioUnitario() { return precioUnitario; }
  public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
  public int getCantidadSolicitada() { return cantidadSolicitada; }
  public void setCantidadSolicitada(int cantidadSolicitada) { this.cantidadSolicitada = cantidadSolicitada; }
  public int getCantidadAceptada() { return cantidadAceptada; }
  public void setCantidadAceptada(int cantidadAceptada) { this.cantidadAceptada = cantidadAceptada; }
  public BigDecimal getSubtotal() { return subtotal; }
  public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
