package cl.nexbyte.model;

import jakarta.persistence.*;

@Entity
@Table(name = "producto_imagen")
public class ProductoImagen {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "id_producto", nullable = false) private Long idProducto;
  @Column(name = "url", nullable = false, length = 512) private String url;
  @Column(name = "es_principal", nullable = false) private Boolean esPrincipal = false;
  @Column(name = "orden", nullable = false) private Integer orden = 0;

  @Column(name = "ancho_px") private Integer anchoPx;
  @Column(name = "alto_px")  private Integer altoPx;
  @Column(name = "peso_bytes") private Integer pesoBytes;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getIdProducto() { return idProducto; }
  public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }
  public String getUrl() { return url; }
  public void setUrl(String url) { this.url = url; }
  public Boolean getEsPrincipal() { return esPrincipal; }
  public void setEsPrincipal(Boolean esPrincipal) { this.esPrincipal = esPrincipal; }
  public Integer getOrden() { return orden; }
  public void setOrden(Integer orden) { this.orden = orden; }
  public Integer getAnchoPx() { return anchoPx; }
  public void setAnchoPx(Integer anchoPx) { this.anchoPx = anchoPx; }
  public Integer getAltoPx() { return altoPx; }
  public void setAltoPx(Integer altoPx) { this.altoPx = altoPx; }
  public Integer getPesoBytes() { return pesoBytes; }
  public void setPesoBytes(Integer pesoBytes) { this.pesoBytes = pesoBytes; }
}
