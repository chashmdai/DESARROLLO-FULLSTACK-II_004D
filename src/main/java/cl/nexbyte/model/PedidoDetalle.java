package cl.nexbyte.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pedido_detalle")
public class PedidoDetalle {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "id_pedido", nullable = false)
  private Long idPedido;

  @Column(name = "id_producto", nullable = false)
  private Long idProducto;

  @Column(nullable = false, length = 64)
  private String sku;

  @Column(name = "nombre_snapshot", nullable = false, length = 180)
  private String nombreSnapshot;

  @Column(name = "precio_unitario_snapshot", nullable = false, precision = 12, scale = 0)
  private BigDecimal precioUnitarioSnapshot;

  @Column(nullable = false)
  private Integer cantidad;

  @Column(nullable = false, precision = 12, scale = 0)
  private BigDecimal subtotal;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getIdPedido() { return idPedido; }
  public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }
  public Long getIdProducto() { return idProducto; }
  public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }
  public String getSku() { return sku; }
  public void setSku(String sku) { this.sku = sku; }
  public String getNombreSnapshot() { return nombreSnapshot; }
  public void setNombreSnapshot(String nombreSnapshot) { this.nombreSnapshot = nombreSnapshot; }
  public BigDecimal getPrecioUnitarioSnapshot() { return precioUnitarioSnapshot; }
  public void setPrecioUnitarioSnapshot(BigDecimal precioUnitarioSnapshot) { this.precioUnitarioSnapshot = precioUnitarioSnapshot; }
  public Integer getCantidad() { return cantidad; }
  public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
  public BigDecimal getSubtotal() { return subtotal; }
  public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
