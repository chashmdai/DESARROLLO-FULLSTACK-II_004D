package cl.nexbyte.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categoria")
public class Categoria {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  private String slug;

  @Column(name = "id_padre")
  private Long idPadre;

  private Boolean visible;

  private Integer orden;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }
  public String getSlug() { return slug; }
  public void setSlug(String slug) { this.slug = slug; }
  public Long getIdPadre() { return idPadre; }
  public void setIdPadre(Long idPadre) { this.idPadre = idPadre; }
  public Boolean getVisible() { return visible; }
  public void setVisible(Boolean visible) { this.visible = visible; }
  public Integer getOrden() { return orden; }
  public void setOrden(Integer orden) { this.orden = orden; }
}
