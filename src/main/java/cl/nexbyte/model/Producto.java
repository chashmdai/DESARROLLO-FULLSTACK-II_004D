package cl.nexbyte.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "producto")
public class Producto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  private String slug;
  private String sku;

  @Column(name = "id_categoria")
  private Long idCategoria;

  @Column(name = "id_marca")
  private Long idMarca;

  @Column(name = "stock_actual")
  private Integer stockActual;

  @Column(name = "publicado_en")
  private LocalDateTime publicadoEn;

  @Column(name = "garantia_meses")
  private Integer garantiaMeses;

  @Column(name = "descripcion_larga")
  private String descripcionLarga;

  @Column(name = "peso_gramos")
  private Integer pesoGramos;

  private String dimensiones;

  @Column(name = "precio_lista")
  private BigDecimal precioLista;

  @Column(name = "precio_oferta")
  private BigDecimal precioOferta;

  @Column(name = "oferta_inicio")
  private LocalDateTime ofertaInicio;

  @Column(name = "oferta_fin")
  private LocalDateTime ofertaFin;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }
  public String getSlug() { return slug; }
  public void setSlug(String slug) { this.slug = slug; }
  public String getSku() { return sku; }
  public void setSku(String sku) { this.sku = sku; }
  public Long getIdCategoria() { return idCategoria; }
  public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }
  public Long getIdMarca() { return idMarca; }
  public void setIdMarca(Long idMarca) { this.idMarca = idMarca; }
  public Integer getStockActual() { return stockActual; }
  public void setStockActual(Integer stockActual) { this.stockActual = stockActual; }
  public LocalDateTime getPublicadoEn() { return publicadoEn; }
  public void setPublicadoEn(LocalDateTime publicadoEn) { this.publicadoEn = publicadoEn; }
  public Integer getGarantiaMeses() { return garantiaMeses; }
  public void setGarantiaMeses(Integer garantiaMeses) { this.garantiaMeses = garantiaMeses; }
  public String getDescripcionLarga() { return descripcionLarga; }
  public void setDescripcionLarga(String descripcionLarga) { this.descripcionLarga = descripcionLarga; }
  public Integer getPesoGramos() { return pesoGramos; }
  public void setPesoGramos(Integer pesoGramos) { this.pesoGramos = pesoGramos; }
  public String getDimensiones() { return dimensiones; }
  public void setDimensiones(String dimensiones) { this.dimensiones = dimensiones; }
  public BigDecimal getPrecioLista() { return precioLista; }
  public void setPrecioLista(BigDecimal precioLista) { this.precioLista = precioLista; }
  public BigDecimal getPrecioOferta() { return precioOferta; }
  public void setPrecioOferta(BigDecimal precioOferta) { this.precioOferta = precioOferta; }
  public LocalDateTime getOfertaInicio() { return ofertaInicio; }
  public void setOfertaInicio(LocalDateTime ofertaInicio) { this.ofertaInicio = ofertaInicio; }
  public LocalDateTime getOfertaFin() { return ofertaFin; }
  public void setOfertaFin(LocalDateTime ofertaFin) { this.ofertaFin = ofertaFin; }
}
