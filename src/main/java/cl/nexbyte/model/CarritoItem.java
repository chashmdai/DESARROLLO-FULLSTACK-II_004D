package cl.nexbyte.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "carrito_item",
  uniqueConstraints = @UniqueConstraint(name="uk_carritoitem_unico", columnNames={"id_carrito","id_producto"}))
public class CarritoItem {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "id_carrito", nullable = false)
  private Long idCarrito;

  @Column(name = "id_producto", nullable = false)
  private Long idProducto;

  @Column(nullable = false, length = 64)
  private String sku;

  @Column(name = "nombre_snapshot", nullable = false, length = 180)
  private String nombreSnapshot;

  @Column(name = "imagen_snapshot", length = 512)
  private String imagenSnapshot;

  @Column(name = "precio_unitario_snapshot", nullable = false, precision = 12, scale = 0)
  private BigDecimal precioUnitarioSnapshot;

  @Column(nullable = false)
  private Integer cantidad;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getIdCarrito() { return idCarrito; }
  public void setIdCarrito(Long idCarrito) { this.idCarrito = idCarrito; }
  public Long getIdProducto() { return idProducto; }
  public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }
  public String getSku() { return sku; }
  public void setSku(String sku) { this.sku = sku; }
  public String getNombreSnapshot() { return nombreSnapshot; }
  public void setNombreSnapshot(String nombreSnapshot) { this.nombreSnapshot = nombreSnapshot; }
  public String getImagenSnapshot() { return imagenSnapshot; }
  public void setImagenSnapshot(String imagenSnapshot) { this.imagenSnapshot = imagenSnapshot; }
  public BigDecimal getPrecioUnitarioSnapshot() { return precioUnitarioSnapshot; }
  public void setPrecioUnitarioSnapshot(BigDecimal precioUnitarioSnapshot) { this.precioUnitarioSnapshot = precioUnitarioSnapshot; }
  public Integer getCantidad() { return cantidad; }
  public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
