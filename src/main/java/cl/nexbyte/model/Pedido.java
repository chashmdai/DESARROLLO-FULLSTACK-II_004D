package cl.nexbyte.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
public class Pedido {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "id_usuario")
  private Long idUsuario;

  @Column(nullable = false, length = 20)
  private String estado = "PENDIENTE";

  @Column(name = "total_bruto", precision = 12, scale = 0, nullable = false)
  private BigDecimal totalBruto;

  @Column(name = "total_descuento", precision = 12, scale = 0, nullable = false)
  private BigDecimal totalDescuento = BigDecimal.ZERO;

  @Column(name = "total_envio", precision = 12, scale = 0, nullable = false)
  private BigDecimal totalEnvio = BigDecimal.ZERO;

  @Column(name = "total_impuestos", precision = 12, scale = 0, nullable = false)
  private BigDecimal totalImpuestos = BigDecimal.ZERO;

  @Column(name = "total_final", precision = 12, scale = 0, nullable = false)
  private BigDecimal totalFinal;

  @Column(nullable = false, length = 3)
  private String moneda = "CLP";

  @Column(name = "medio_pago", nullable = false, length = 30)
  private String medioPago;

  @Column(name = "comprobante_url", length = 512)
  private String comprobanteUrl;

  @Column(name = "direccion_envio_snapshot", columnDefinition = "json")
  private String direccionEnvioSnapshot;

  @Column(name = "metodo_envio", length = 60)
  private String metodoEnvio;

  @Column(length = 80)
  private String tracking;

  @Column(name = "creado_en", nullable = false)
  private LocalDateTime creadoEn = LocalDateTime.now();

  @Column(name = "confirmado_en") private LocalDateTime confirmadoEn;
  @Column(name = "enviado_en") private LocalDateTime enviadoEn;
  @Column(name = "entregado_en") private LocalDateTime entregadoEn;
  @Column(name = "cancelado_en") private LocalDateTime canceladoEn;
  @Column(name = "devuelto_en") private LocalDateTime devueltoEn;

  @Column(name = "notas_admin", length = 500)
  private String notasAdmin;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getIdUsuario() { return idUsuario; }
  public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
  public String getEstado() { return estado; }
  public void setEstado(String estado) { this.estado = estado; }
  public BigDecimal getTotalBruto() { return totalBruto; }
  public void setTotalBruto(BigDecimal totalBruto) { this.totalBruto = totalBruto; }
  public BigDecimal getTotalDescuento() { return totalDescuento; }
  public void setTotalDescuento(BigDecimal totalDescuento) { this.totalDescuento = totalDescuento; }
  public BigDecimal getTotalEnvio() { return totalEnvio; }
  public void setTotalEnvio(BigDecimal totalEnvio) { this.totalEnvio = totalEnvio; }
  public BigDecimal getTotalImpuestos() { return totalImpuestos; }
  public void setTotalImpuestos(BigDecimal totalImpuestos) { this.totalImpuestos = totalImpuestos; }
  public BigDecimal getTotalFinal() { return totalFinal; }
  public void setTotalFinal(BigDecimal totalFinal) { this.totalFinal = totalFinal; }
  public String getMoneda() { return moneda; }
  public void setMoneda(String moneda) { this.moneda = moneda; }
  public String getMedioPago() { return medioPago; }
  public void setMedioPago(String medioPago) { this.medioPago = medioPago; }
  public String getComprobanteUrl() { return comprobanteUrl; }
  public void setComprobanteUrl(String comprobanteUrl) { this.comprobanteUrl = comprobanteUrl; }
  public String getDireccionEnvioSnapshot() { return direccionEnvioSnapshot; }
  public void setDireccionEnvioSnapshot(String direccionEnvioSnapshot) { this.direccionEnvioSnapshot = direccionEnvioSnapshot; }
  public String getMetodoEnvio() { return metodoEnvio; }
  public void setMetodoEnvio(String metodoEnvio) { this.metodoEnvio = metodoEnvio; }
  public String getTracking() { return tracking; }
  public void setTracking(String tracking) { this.tracking = tracking; }
  public LocalDateTime getCreadoEn() { return creadoEn; }
  public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
  public LocalDateTime getConfirmadoEn() { return confirmadoEn; }
  public void setConfirmadoEn(LocalDateTime confirmadoEn) { this.confirmadoEn = confirmadoEn; }
  public LocalDateTime getEnviadoEn() { return enviadoEn; }
  public void setEnviadoEn(LocalDateTime enviadoEn) { this.enviadoEn = enviadoEn; }
  public LocalDateTime getEntregadoEn() { return entregadoEn; }
  public void setEntregadoEn(LocalDateTime entregadoEn) { this.entregadoEn = entregadoEn; }
  public LocalDateTime getCanceladoEn() { return canceladoEn; }
  public void setCanceladoEn(LocalDateTime canceladoEn) { this.canceladoEn = canceladoEn; }
  public LocalDateTime getDevueltoEn() { return devueltoEn; }
  public void setDevueltoEn(LocalDateTime devueltoEn) { this.devueltoEn = devueltoEn; }
  public String getNotasAdmin() { return notasAdmin; }
  public void setNotasAdmin(String notasAdmin) { this.notasAdmin = notasAdmin; }
}
