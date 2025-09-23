package cl.nexbyte.repository.projection;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public interface ProductoDetalleView {
  Long getId();
  String getNombre();
  String getSlug();
  String getSku();
  Long getIdCategoria();
  Long getIdMarca();
  Integer getStockActual();
  Integer getGarantiaMeses();
  String getDescripcionLarga();
  Integer getPesoGramos();
  String getDimensiones();
  LocalDateTime getPublicadoEn();
  BigDecimal getPrecioVigente();
  BigDecimal getPrecioLista();
  BigDecimal getPrecioOferta();
  LocalDateTime getOfertaInicio();
  LocalDateTime getOfertaFin();
}
