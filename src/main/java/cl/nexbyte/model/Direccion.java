package cl.nexbyte.model;

import jakarta.persistence.*;

@Entity
@Table(name = "direccion")
public class Direccion {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "id_usuario")
  private Long idUsuario;

  private String alias;

  @Column(name = "nombre_receptor")
  private String nombreReceptor;

  private String telefono;

  @Column(name = "id_region")
  private Long idRegion;

  @Column(name = "id_comuna")
  private Long idComuna;

  private String calle;
  private String numero;
  private String depto;

  @Column(name = "codigo_postal")
  private String codigoPostal;

  private String notas;

  @Column(name = "es_predeterminada")
  private Boolean esPredeterminada = Boolean.FALSE;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getIdUsuario() { return idUsuario; }
  public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
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
