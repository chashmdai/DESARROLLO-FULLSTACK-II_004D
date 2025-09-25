package cl.nexbyte.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido_historial")
public class PedidoHistorial {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "id_pedido", nullable = false)
  private Long idPedido;

  @Column(name = "estado_anterior", nullable = false, length = 20)
  private String estadoAnterior;

  @Column(name = "estado_nuevo", nullable = false, length = 20)
  private String estadoNuevo;

  @Column(name = "cambiado_por")
  private Long cambiadoPor;

  @Column(length = 300)
  private String motivo;

  @Column(name = "creado_en", nullable = false)
  private LocalDateTime creadoEn = LocalDateTime.now();

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getIdPedido() { return idPedido; }
  public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }
  public String getEstadoAnterior() { return estadoAnterior; }
  public void setEstadoAnterior(String estadoAnterior) { this.estadoAnterior = estadoAnterior; }
  public String getEstadoNuevo() { return estadoNuevo; }
  public void setEstadoNuevo(String estadoNuevo) { this.estadoNuevo = estadoNuevo; }
  public Long getCambiadoPor() { return cambiadoPor; }
  public void setCambiadoPor(Long cambiadoPor) { this.cambiadoPor = cambiadoPor; }
  public String getMotivo() { return motivo; }
  public void setMotivo(String motivo) { this.motivo = motivo; }
  public LocalDateTime getCreadoEn() { return creadoEn; }
  public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
}
