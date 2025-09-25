package cl.nexbyte.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDetailResponse {
  private Long id;
  private String estado;
  private LocalDateTime creadoEn;
  private BigDecimal totalBruto;
  private BigDecimal totalDescuento;
  private BigDecimal totalEnvio;
  private BigDecimal totalImpuestos;
  private BigDecimal totalFinal;
  private String moneda;
  private String metodoEnvio;
  private String direccionEnvioSnapshot;
  private List<PedidoItemView> items;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getEstado() { return estado; }
  public void setEstado(String estado) { this.estado = estado; }
  public LocalDateTime getCreadoEn() { return creadoEn; }
  public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
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
  public String getMetodoEnvio() { return metodoEnvio; }
  public void setMetodoEnvio(String metodoEnvio) { this.metodoEnvio = metodoEnvio; }
  public String getDireccionEnvioSnapshot() { return direccionEnvioSnapshot; }
  public void setDireccionEnvioSnapshot(String direccionEnvioSnapshot) { this.direccionEnvioSnapshot = direccionEnvioSnapshot; }
  public List<PedidoItemView> getItems() { return items; }
  public void setItems(List<PedidoItemView> items) { this.items = items; }
}
