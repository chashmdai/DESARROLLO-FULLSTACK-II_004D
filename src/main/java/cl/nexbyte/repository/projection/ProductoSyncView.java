package cl.nexbyte.repository.projection;

import java.math.BigDecimal;

public interface ProductoSyncView {
  Long getId();
  String getSku();
  String getNombre();
  String getSlug();
  Integer getStockActual();
  BigDecimal getPrecioVigente();
}
