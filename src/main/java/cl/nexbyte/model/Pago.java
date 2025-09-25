package cl.nexbyte.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago", uniqueConstraints = @UniqueConstraint(name="uk_pago_pedido", columnNames = "id_pedido"))
public class Pago {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "id_pedido", nullable = false)
  private Long idPedido;

  @Column(nullable = false, length = 30)
  private String proveedor; // WEBPAY | MERCADO_PAGO | TRANSFERENCIA | CONTRA_ENTREGA

  @Column(nullable = false, precision = 12, scale = 0)
  private BigDecimal monto;

  @Column(name = "estado_pago", nullable = false, length = 20)
  private String estadoPago = "PENDIENTE";

  @Column(name = "codigo_autorizacion", length = 80)
  private String codigoAutorizacion;

  @Column(name = "payload_proveedor", columnDefinition = "json")
  private String payloadProveedor;

  @Column(name = "creado_en", nullable = false)
  private LocalDateTime creadoEn = LocalDateTime.now();

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getIdPedido() { return idPedido; }
  public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }
  public String getProveedor() { return proveedor; }
  public void setProveedor(String proveedor) { this.proveedor = proveedor; }
  public BigDecimal getMonto() { return monto; }
  public void setMonto(BigDecimal monto) { this.monto = monto; }
  public String getEstadoPago() { return estadoPago; }
  public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }
  public String getCodigoAutorizacion() { return codigoAutorizacion; }
  public void setCodigoAutorizacion(String codigoAutorizacion) { this.codigoAutorizacion = codigoAutorizacion; }
  public String getPayloadProveedor() { return payloadProveedor; }
  public void setPayloadProveedor(String payloadProveedor) { this.payloadProveedor = payloadProveedor; }
  public LocalDateTime getCreadoEn() { return creadoEn; }
  public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
}

// Para movimientos de inventario lo implementaré al confirmar el pago.