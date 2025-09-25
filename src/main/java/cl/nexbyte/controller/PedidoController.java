package cl.nexbyte.controller;

import cl.nexbyte.api.dto.PedidoCreateRequest;
import cl.nexbyte.api.dto.PedidoDetailResponse;
import cl.nexbyte.api.dto.PedidoSummaryResponse;
import cl.nexbyte.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

  private final PedidoService service;

  public PedidoController(PedidoService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<PedidoSummaryResponse> crear(Authentication auth,
                                                     @Valid @RequestBody PedidoCreateRequest req) {
    return ResponseEntity.ok(service.crearPedidoDesdeCarrito(auth, req));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PedidoDetailResponse> detalle(Authentication auth, @PathVariable Long id) {
    return ResponseEntity.ok(service.getDetalle(auth, id));
  }

  @GetMapping
  public ResponseEntity<Page<PedidoSummaryResponse>> listar(Authentication auth,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(service.listar(auth, page, size));
  }
}
