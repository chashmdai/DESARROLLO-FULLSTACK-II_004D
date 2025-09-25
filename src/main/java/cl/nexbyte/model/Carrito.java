package cl.nexbyte.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "carrito", uniqueConstraints = @UniqueConstraint(name="uk_carrito_usuario", columnNames = "id_usuario"))
public class Carrito {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "id_usuario", nullable = false)
  private Long idUsuario;

  @Column(name = "creado_en", nullable = false)
  private LocalDateTime creadoEn = LocalDateTime.now();

  @Column(name = "actualizado_en", nullable = false)
  private LocalDateTime actualizadoEn = LocalDateTime.now();

  @PreUpdate
  public void onUpdate() { this.actualizadoEn = LocalDateTime.now(); }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getIdUsuario() { return idUsuario; }
  public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
  public LocalDateTime getCreadoEn() { return creadoEn; }
  public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
  public LocalDateTime getActualizadoEn() { return actualizadoEn; }
  public void setActualizadoEn(LocalDateTime actualizadoEn) { this.actualizadoEn = actualizadoEn; }
}
