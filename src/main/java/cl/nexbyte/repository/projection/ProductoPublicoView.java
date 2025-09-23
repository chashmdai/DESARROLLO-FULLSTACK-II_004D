package cl.nexbyte.repository.projection;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public interface ProductoPublicoView {
  Long getId();
  String getNombre();
  String getSlug();
  String getSku();
  Long getIdCategoria();
  Long getIdMarca();
  Integer getStockActual();
  LocalDateTime getPublicadoEn();
  BigDecimal getPrecioVigente();
  String getImagenUrl();
}
