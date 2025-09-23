package cl.nexbyte.model;

import jakarta.persistence.*;

@Entity
@Table(name = "producto_imagen")
public class ProductoImagen {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "id_producto")
  private Long idProducto;

  private String url;

  @Column(name = "es_principal")
  private Boolean esPrincipal;

  private Integer orden;

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
}
