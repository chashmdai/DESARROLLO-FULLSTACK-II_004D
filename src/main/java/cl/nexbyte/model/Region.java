package cl.nexbyte.model;

import jakarta.persistence.*;

@Entity
@Table(name = "region")
public class Region {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nombre;

  @Column(name = "iso_3166_2")
  private String iso3166_2;

  private Integer orden;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }
  public String getIso3166_2() { return iso3166_2; }
  public void setIso3166_2(String iso3166_2) { this.iso3166_2 = iso3166_2; }
  public Integer getOrden() { return orden; }
  public void setOrden(Integer orden) { this.orden = orden; }
}
