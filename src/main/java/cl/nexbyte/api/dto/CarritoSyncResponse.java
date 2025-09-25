package cl.nexbyte.api.dto;

import java.math.BigDecimal;
import java.util.List;

public class CarritoSyncResponse {
  private List<CarritoItemSync> items;
  private BigDecimal totalBruto;
  private BigDecimal totalDescuento;
  private BigDecimal totalFinal;
  private String moneda = "CLP";
  private List<String> warnings;

  public List<CarritoItemSync> getItems() { return items; }
  public void setItems(List<CarritoItemSync> items) { this.items = items; }
  public BigDecimal getTotalBruto() { return totalBruto; }
  public void setTotalBruto(BigDecimal totalBruto) { this.totalBruto = totalBruto; }
  public BigDecimal getTotalDescuento() { return totalDescuento; }
  public void setTotalDescuento(BigDecimal totalDescuento) { this.totalDescuento = totalDescuento; }
  public BigDecimal getTotalFinal() { return totalFinal; }
  public void setTotalFinal(BigDecimal totalFinal) { this.totalFinal = totalFinal; }
  public String getMoneda() { return moneda; }
  public void setMoneda(String moneda) { this.moneda = moneda; }
  public List<String> getWarnings() { return warnings; }
  public void setWarnings(List<String> warnings) { this.warnings = warnings; }
}
