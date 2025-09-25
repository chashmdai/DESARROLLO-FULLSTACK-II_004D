package cl.nexbyte.api.dto;

import jakarta.validation.constraints.*;

public class DireccionRequest {
  private String alias;
  @NotBlank private String nombreReceptor;
  @NotBlank @Size(min=8, max=30) private String telefono;
  @NotNull private Long idRegion;
  @NotNull private Long idComuna;
  @NotBlank private String calle;
  @NotBlank private String numero;
  private String depto;
  private String codigoPostal;
  private String notas;
  private Boolean esPredeterminada = Boolean.FALSE;

  public String getAlias() { return alias; }
  public void setAlias(String alias) { this.alias = alias; }
  public String getNombreReceptor() { return nombreReceptor; }
  public void setNombreReceptor(String nombreReceptor) { this.nombreReceptor = nombreReceptor; }
  public String getTelefono() { return telefono; }
  public void setTelefono(String telefono) { this.telefono = telefono; }
  public Long getIdRegion() { return idRegion; }
  public void setIdRegion(Long idRegion) { this.idRegion = idRegion; }
  public Long getIdComuna() { return idComuna; }
  public void setIdComuna(Long idComuna) { this.idComuna = idComuna; }
  public String getCalle() { return calle; }
  public void setCalle(String calle) { this.calle = calle; }
  public String getNumero() { return numero; }
  public void setNumero(String numero) { this.numero = numero; }
  public String getDepto() { return depto; }
  public void setDepto(String depto) { this.depto = depto; }
  public String getCodigoPostal() { return codigoPostal; }
  public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
  public String getNotas() { return notas; }
  public void setNotas(String notas) { this.notas = notas; }
  public Boolean getEsPredeterminada() { return esPredeterminada; }
  public void setEsPredeterminada(Boolean esPredeterminada) { this.esPredeterminada = esPredeterminada; }
}
