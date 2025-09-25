package cl.nexbyte.service;

import cl.nexbyte.api.dto.*;
import cl.nexbyte.model.*;
import cl.nexbyte.repository.*;
import cl.nexbyte.repository.projection.ProductoSyncView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PedidoService {

  private final UsuarioRepository usuarioRepo;
  private final DireccionRepository direccionRepo;
  private final CarritoRepository carritoRepo;
  private final CarritoItemRepository carritoItemRepo;
  private final ProductoSyncRepository productoRepo;
  private final PedidoRepository pedidoRepo;
  private final PedidoDetalleRepository detalleRepo;
  private final PedidoHistorialRepository historialRepo;
  private final PagoRepository pagoRepo;
  private final ObjectMapper objectMapper;

  public PedidoService(UsuarioRepository usuarioRepo,
                       DireccionRepository direccionRepo,
                       CarritoRepository carritoRepo,
                       CarritoItemRepository carritoItemRepo,
                       ProductoSyncRepository productoRepo,
                       PedidoRepository pedidoRepo,
                       PedidoDetalleRepository detalleRepo,
                       PedidoHistorialRepository historialRepo,
                       PagoRepository pagoRepo,
                       ObjectMapper objectMapper) {
    this.usuarioRepo = usuarioRepo;
    this.direccionRepo = direccionRepo;
    this.carritoRepo = carritoRepo;
    this.carritoItemRepo = carritoItemRepo;
    this.productoRepo = productoRepo;
    this.pedidoRepo = pedidoRepo;
    this.detalleRepo = detalleRepo;
    this.historialRepo = historialRepo;
    this.pagoRepo = pagoRepo;
    this.objectMapper = objectMapper;
  }

  private Long userId(Authentication auth) {
    return usuarioRepo.findByEmail(auth.getName()).map(Usuario::getId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado"));
  }

  private Carrito ensureCart(Long userId) {
    return carritoRepo.findByIdUsuario(userId).orElseGet(() -> {
      Carrito c = new Carrito();
      c.setIdUsuario(userId);
      return carritoRepo.save(c);
    });
  }

  @Transactional
  public PedidoSummaryResponse crearPedidoDesdeCarrito(Authentication auth, PedidoCreateRequest req) {
    Long uid = userId(auth);
    Carrito carrito = ensureCart(uid);
    List<CarritoItem> items = carritoItemRepo.findByIdCarritoOrderByIdDesc(carrito.getId());
    if (items.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Carrito vacío");
    }

    var dir = direccionRepo.findById(req.getIdDireccion())
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dirección no existe"));
    if (!dir.getIdUsuario().equals(uid)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes usar esta dirección");
    }
    Map<String, Object> dirSnap = new LinkedHashMap<>();
    dirSnap.put("id", dir.getId());
    dirSnap.put("alias", dir.getAlias());
    dirSnap.put("nombreReceptor", dir.getNombreReceptor());
    dirSnap.put("telefono", dir.getTelefono());
    dirSnap.put("idRegion", dir.getIdRegion());
    dirSnap.put("idComuna", dir.getIdComuna());
    dirSnap.put("calle", dir.getCalle());
    dirSnap.put("numero", dir.getNumero());
    dirSnap.put("depto", dir.getDepto());
    dirSnap.put("codigoPostal", dir.getCodigoPostal());
    dirSnap.put("notas", dir.getNotas());

    String dirJson;
    try { dirJson = objectMapper.writeValueAsString(dirSnap); }
    catch (Exception e) { throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo serializar la dirección"); }

    Set<Long> ids = items.stream().map(CarritoItem::getIdProducto).collect(Collectors.toSet());
    List<ProductoSyncView> productos = productoRepo.findPublicInfoByIds(ids);
    Map<Long, ProductoSyncView> byId = productos.stream().collect(Collectors.toMap(ProductoSyncView::getId, p -> p));

    BigDecimal total = BigDecimal.ZERO;
    List<PedidoDetalle> detalles = new ArrayList<>();

    for (CarritoItem it : items) {
      ProductoSyncView p = byId.get(it.getIdProducto());
      if (p == null) {
        continue;
      }
      int cant = Math.max(0, Math.min(it.getCantidad(), Optional.ofNullable(p.getStockActual()).orElse(0)));
      if (cant <= 0) continue;

      BigDecimal precio = Optional.ofNullable(p.getPrecioVigente()).orElse(BigDecimal.ZERO);
      BigDecimal sub = precio.multiply(BigDecimal.valueOf(cant));
      total = total.add(sub);

      PedidoDetalle d = new PedidoDetalle();
      d.setIdProducto(p.getId());
      d.setSku(p.getSku());
      d.setNombreSnapshot(p.getNombre());
      d.setCantidad(cant);
      d.setPrecioUnitarioSnapshot(precio);
      d.setSubtotal(sub);
      detalles.add(d);
    }

    if (detalles.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No hay items válidos para crear el pedido");
    }

    Pedido pedido = new Pedido();
    pedido.setIdUsuario(uid);
    pedido.setEstado("PENDIENTE");
    pedido.setTotalBruto(total);
    pedido.setTotalDescuento(BigDecimal.ZERO);
    pedido.setTotalEnvio(BigDecimal.ZERO);
    pedido.setTotalImpuestos(BigDecimal.ZERO);
    pedido.setTotalFinal(total);
    pedido.setMoneda("CLP");
    pedido.setMedioPago(req.getMedioPago());
    pedido.setComprobanteUrl(req.getComprobanteUrl());
    pedido.setMetodoEnvio(req.getMetodoEnvio());
    pedido.setDireccionEnvioSnapshot(dirJson);
    pedido = pedidoRepo.save(pedido);

    Long idPedido = pedido.getId();
    for (PedidoDetalle d : detalles) {
      d.setIdPedido(idPedido);
    }
    detalleRepo.saveAll(detalles);

    PedidoHistorial h = new PedidoHistorial();
    h.setIdPedido(idPedido);
    h.setEstadoAnterior("PENDIENTE");
    h.setEstadoNuevo("PENDIENTE");
    h.setCambiadoPor(uid);
    h.setMotivo("Creación de pedido");
    historialRepo.save(h);

    Pago pago = new Pago();
    pago.setIdPedido(idPedido);
    pago.setProveedor(req.getMedioPago());
    pago.setMonto(total);
    pago.setEstadoPago("PENDIENTE");
    pagoRepo.save(pago);

    carritoItemRepo.deleteByIdCarrito(carrito.getId());

    return new PedidoSummaryResponse(idPedido, pedido.getEstado(), pedido.getTotalFinal(), pedido.getMoneda());
  }

  @Transactional(readOnly = true)
  public PedidoDetailResponse getDetalle(Authentication auth, Long idPedido) {
    Long uid = userId(auth);
    Pedido p = pedidoRepo.findById(idPedido).orElseThrow(() ->
      new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado")
    );
    if (p.getIdUsuario() != null && !p.getIdUsuario().equals(uid)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes ver este pedido");
    }

    List<PedidoDetalle> dets = detalleRepo.findByIdPedido(idPedido);
    List<PedidoItemView> items = dets.stream().map(d -> {
      PedidoItemView v = new PedidoItemView();
      v.setIdProducto(d.getIdProducto());
      v.setSku(d.getSku());
      v.setNombre(d.getNombreSnapshot());
      v.setCantidad(d.getCantidad());
      v.setPrecioUnitario(d.getPrecioUnitarioSnapshot());
      v.setSubtotal(d.getSubtotal());
      return v;
    }).toList();

    PedidoDetailResponse r = new PedidoDetailResponse();
    r.setId(p.getId());
    r.setEstado(p.getEstado());
    r.setCreadoEn(p.getCreadoEn());
    r.setTotalBruto(p.getTotalBruto());
    r.setTotalDescuento(p.getTotalDescuento());
    r.setTotalEnvio(p.getTotalEnvio());
    r.setTotalImpuestos(p.getTotalImpuestos());
    r.setTotalFinal(p.getTotalFinal());
    r.setMoneda(p.getMoneda());
    r.setMetodoEnvio(p.getMetodoEnvio());
    r.setDireccionEnvioSnapshot(p.getDireccionEnvioSnapshot());
    r.setItems(items);
    return r;
  }

  @Transactional(readOnly = true)
  public Page<PedidoSummaryResponse> listar(Authentication auth, int page, int size) {
    Long uid = userId(auth);
    var pg = pedidoRepo.findByIdUsuarioOrderByCreadoEnDesc(uid, PageRequest.of(page, size));
    return pg.map(p -> new PedidoSummaryResponse(p.getId(), p.getEstado(), p.getTotalFinal(), p.getMoneda()));
  }
}