package cl.nexbyte.service;

import cl.nexbyte.api.dto.*;
import cl.nexbyte.repository.ProductoImagenLiteRepository;
import cl.nexbyte.repository.ProductoSyncRepository;
import cl.nexbyte.repository.projection.ImagenPrincipalRow;
import cl.nexbyte.repository.projection.ProductoSyncView;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarritoService {
  private final ProductoSyncRepository productoRepo;
  private final ProductoImagenLiteRepository imagenRepo;

  public CarritoService(ProductoSyncRepository productoRepo, ProductoImagenLiteRepository imagenRepo) {
    this.productoRepo = productoRepo;
    this.imagenRepo = imagenRepo;
  }

  public CarritoSyncResponse sync(CarritoSyncRequest req) {
    Map<Long, Integer> solicitadas = new LinkedHashMap<>();
    for (CarritoItemRequest it : req.getItems()) {
      solicitadas.merge(it.getIdProducto(), Math.max(1, it.getCantidad()), Integer::sum);
    }

    var ids = solicitadas.keySet();
    List<ProductoSyncView> productos = ids.isEmpty() ? List.of() : productoRepo.findPublicInfoByIds(ids);
    Map<Long, ProductoSyncView> byId = productos.stream().collect(Collectors.toMap(ProductoSyncView::getId, p -> p));

    List<ImagenPrincipalRow> imgs = ids.isEmpty() ? List.of() : imagenRepo.findPrincipalUrls(ids);
    Map<Long, String> imgById = new HashMap<>();
    for (ImagenPrincipalRow r : imgs) imgById.putIfAbsent(r.getIdProducto(), r.getUrl()); // primer orden

    List<CarritoItemSync> items = new ArrayList<>();
    List<String> warnings = new ArrayList<>();
    BigDecimal total = BigDecimal.ZERO;

    for (Map.Entry<Long, Integer> e : solicitadas.entrySet()) {
      Long id = e.getKey();
      int cant = e.getValue();
      ProductoSyncView p = byId.get(id);
      if (p == null) {
        warnings.add("Producto " + id + " no disponible.");
        continue;
      }
      int aceptada = Math.max(0, Math.min(cant, Optional.ofNullable(p.getStockActual()).orElse(0)));
      if (aceptada < cant) {
        warnings.add("Cantidad ajustada para " + p.getSku() + " a " + aceptada + " por stock.");
      }
      BigDecimal unit = p.getPrecioVigente() != null ? p.getPrecioVigente() : BigDecimal.ZERO;
      BigDecimal sub = unit.multiply(BigDecimal.valueOf(aceptada));
      total = total.add(sub);

      CarritoItemSync o = new CarritoItemSync();
      o.setIdProducto(id);
      o.setSku(p.getSku());
      o.setNombre(p.getNombre());
      o.setImagenUrl(imgById.get(id));
      o.setPrecioUnitario(unit);
      o.setCantidadSolicitada(cant);
      o.setCantidadAceptada(aceptada);
      o.setSubtotal(sub);
      items.add(o);
    }

    CarritoSyncResponse r = new CarritoSyncResponse();
    r.setItems(items);
    r.setTotalBruto(total);
    r.setTotalDescuento(BigDecimal.ZERO);
    r.setTotalFinal(total);
    r.setWarnings(warnings);
    return r;
  }
}
