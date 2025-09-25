package cl.nexbyte.api.dto;

import jakarta.validation.constraints.NotNull;

public class PedidoCreateRequest {
  @NotNull
  private Long idDireccion;

  @NotNull
  private String medioPago;   // TRANSFERENCIA | CONTRA_ENTREGA | WEBPAY | MERCADO_PAGO

  private String metodoEnvio; // p.ej. RETIRA_TIENDA / DESPACHO
  private String comprobanteUrl;
  private String notas;

  public Long getIdDireccion() { return idDireccion; }
  public void setIdDireccion(Long idDireccion) { this.idDireccion = idDireccion; }
  public String getMedioPago() { return medioPago; }
  public void setMedioPago(String medioPago) { this.medioPago = medioPago; }
  public String getMetodoEnvio() { return metodoEnvio; }
  public void setMetodoEnvio(String metodoEnvio) { this.metodoEnvio = metodoEnvio; }
  public String getComprobanteUrl() { return comprobanteUrl; }
  public void setComprobanteUrl(String comprobanteUrl) { this.comprobanteUrl = comprobanteUrl; }
  public String getNotas() { return notas; }
  public void setNotas(String notas) { this.notas = notas; }
}
