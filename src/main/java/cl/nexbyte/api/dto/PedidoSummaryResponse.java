package cl.nexbyte.api.dto;

import java.math.BigDecimal;

public class PedidoSummaryResponse {
  private Long id;
  private String estado;
  private BigDecimal totalFinal;
  private String moneda;

  public PedidoSummaryResponse() {}
  public PedidoSummaryResponse(Long id, String estado, BigDecimal totalFinal, String moneda) {
    this.id = id; this.estado = estado; this.totalFinal = totalFinal; this.moneda = moneda;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getEstado() { return estado; }
  public void setEstado(String estado) { this.estado = estado; }
  public BigDecimal getTotalFinal() { return totalFinal; }
  public void setTotalFinal(BigDecimal totalFinal) { this.totalFinal = totalFinal; }
  public String getMoneda() { return moneda; }
  public void setMoneda(String moneda) { this.moneda = moneda; }
}
