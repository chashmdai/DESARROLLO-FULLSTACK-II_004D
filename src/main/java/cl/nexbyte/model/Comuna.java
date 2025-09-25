package cl.nexbyte.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comuna")
public class Comuna {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "id_region")
  private Long idRegion;

  private String nombre;
  private Integer orden;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getIdRegion() { return idRegion; }
  public void setIdRegion(Long idRegion) { this.idRegion = idRegion; }
  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }
  public Integer getOrden() { return orden; }
  public void setOrden(Integer orden) { this.orden = orden; }
}
