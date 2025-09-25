package cl.nexbyte.service;

import cl.nexbyte.api.dto.*;
import cl.nexbyte.model.Carrito;
import cl.nexbyte.model.CarritoItem;
import cl.nexbyte.model.Usuario;
import cl.nexbyte.repository.*;
import cl.nexbyte.repository.projection.ImagenPrincipalRow;
import cl.nexbyte.repository.projection.ProductoSyncView;
//import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarritoPersistService {

  private final UsuarioRepository usuarioRepo;
  private final CarritoRepository carritoRepo;
  private final CarritoItemRepository itemRepo;
  private final ProductoSyncRepository productoRepo;
  private final ProductoImagenLiteRepository imagenRepo;

  public CarritoPersistService(UsuarioRepository usuarioRepo,
                               CarritoRepository carritoRepo,
                               CarritoItemRepository itemRepo,
                               ProductoSyncRepository productoRepo,
                               ProductoImagenLiteRepository imagenRepo) {
    this.usuarioRepo = usuarioRepo;
    this.carritoRepo = carritoRepo;
    this.itemRepo = itemRepo;
    this.productoRepo = productoRepo;
    this.imagenRepo = imagenRepo;
  }

  private Long userId(Authentication auth) {
    return usuarioRepo.findByEmail(auth.getName()).map(Usuario::getId).orElseThrow();
  }

  private Carrito ensureCart(Long userId) {
    return carritoRepo.findByIdUsuario(userId).orElseGet(() -> {
      Carrito c = new Carrito();
      c.setIdUsuario(userId);
      return carritoRepo.save(c);
    });
  }

  @Transactional
  public CarritoSyncResponse getNormalized(Authentication auth) {
    Long uid = userId(auth);
    Carrito c = ensureCart(uid);
    List<CarritoItem> items = itemRepo.findByIdCarritoOrderByIdDesc(c.getId());

    if (items.isEmpty()) {
      CarritoSyncResponse r = new CarritoSyncResponse();
      r.setItems(List.of());
      r.setTotalBruto(BigDecimal.ZERO);
      r.setTotalDescuento(BigDecimal.ZERO);
      r.setTotalFinal(BigDecimal.ZERO);
      r.setWarnings(List.of());
      return r;
    }

    var ids = items.stream().map(CarritoItem::getIdProducto).collect(Collectors.toSet());
    List<ProductoSyncView> productos = productoRepo.findPublicInfoByIds(ids);
    Map<Long, ProductoSyncView> byId = productos.stream().collect(Collectors.toMap(ProductoSyncView::getId, p -> p));

    List<ImagenPrincipalRow> imgs = imagenRepo.findPrincipalUrls(ids);
    Map<Long, String> imgById = new HashMap<>();
    for (ImagenPrincipalRow r : imgs) imgById.putIfAbsent(r.getIdProducto(), r.getUrl());

    BigDecimal total = BigDecimal.ZERO;
    List<String> warnings = new ArrayList<>();
    List<CarritoItemSync> out = new ArrayList<>();

    for (CarritoItem it : items) {
      ProductoSyncView p = byId.get(it.getIdProducto());
      if (p == null) {
        warnings.add("Producto " + it.getIdProducto() + " no disponible actualmente.");
        continue;
      }
      int solicitada = it.getCantidad();
      int aceptada = Math.max(0, Math.min(solicitada, Optional.ofNullable(p.getStockActual()).orElse(0)));

      if (aceptada < solicitada) {
        warnings.add("Cantidad ajustada para " + p.getSku() + " a " + aceptada + " por stock.");
      }

      BigDecimal precio = p.getPrecioVigente() != null ? p.getPrecioVigente() : BigDecimal.ZERO;
      BigDecimal sub = precio.multiply(BigDecimal.valueOf(aceptada));
      total = total.add(sub);

      CarritoItemSync row = new CarritoItemSync();
      row.setIdProducto(p.getId());
      row.setSku(p.getSku());
      row.setNombre(p.getNombre());
      row.setImagenUrl(imgById.get(p.getId()));
      row.setPrecioUnitario(precio);
      row.setCantidadSolicitada(solicitada);
      row.setCantidadAceptada(aceptada);
      row.setSubtotal(sub);
      out.add(row);
    }

    CarritoSyncResponse r = new CarritoSyncResponse();
    r.setItems(out);
    r.setTotalBruto(total);
    r.setTotalDescuento(BigDecimal.ZERO);
    r.setTotalFinal(total);
    r.setWarnings(warnings);
    return r;
  }

  @Transactional
  public CarritoSyncResponse replace(Authentication auth, CarritoSyncRequest req) {
    Long uid = userId(auth);
    Carrito c = ensureCart(uid);

    Map<Long, Integer> solicitadas = new LinkedHashMap<>();
    for (CarritoItemRequest it : req.getItems()) {
      solicitadas.merge(it.getIdProducto(), Math.max(1, it.getCantidad()), Integer::sum);
    }

    var ids = solicitadas.keySet();
    List<ProductoSyncView> productos = ids.isEmpty() ? List.of() : productoRepo.findPublicInfoByIds(ids);
    Map<Long, ProductoSyncView> byId = productos.stream().collect(Collectors.toMap(ProductoSyncView::getId, p -> p));
    List<ImagenPrincipalRow> imgs = ids.isEmpty() ? List.of() : imagenRepo.findPrincipalUrls(ids);
    Map<Long, String> imgById = new HashMap<>();
    for (ImagenPrincipalRow r : imgs) imgById.putIfAbsent(r.getIdProducto(), r.getUrl());

    itemRepo.deleteByIdCarrito(c.getId());

    List<CarritoItem> nuevos = new ArrayList<>();
    for (Map.Entry<Long, Integer> e : solicitadas.entrySet()) {
      Long idProd = e.getKey();
      int cantSolic = e.getValue();
      ProductoSyncView p = byId.get(idProd);
      if (p == null) continue;

      int stock = Optional.ofNullable(p.getStockActual()).orElse(0);
      int cantAceptada = Math.max(0, Math.min(cantSolic, stock));
      if (cantAceptada <= 0) continue;

      BigDecimal precio = p.getPrecioVigente() != null ? p.getPrecioVigente() : BigDecimal.ZERO;

      CarritoItem ci = new CarritoItem();
      ci.setIdCarrito(c.getId());
      ci.setIdProducto(p.getId());
      ci.setSku(p.getSku());
      ci.setNombreSnapshot(p.getNombre());
      ci.setImagenSnapshot(imgById.get(p.getId()));
      ci.setPrecioUnitarioSnapshot(precio);
      ci.setCantidad(cantAceptada);
      nuevos.add(ci);
    }
    if (!nuevos.isEmpty()) itemRepo.saveAll(nuevos);

    return getNormalized(auth);
  }

  @Transactional
  public void clear(Authentication auth) {
    Long uid = userId(auth);
    Carrito c = ensureCart(uid);
    itemRepo.deleteByIdCarrito(c.getId());
  }

  @Transactional
  public void removeItem(Authentication auth, Long idProducto) {
    Long uid = userId(auth);
    Carrito c = ensureCart(uid);
    itemRepo.deleteByIdCarritoAndIdProducto(c.getId(), idProducto);
  }
}
