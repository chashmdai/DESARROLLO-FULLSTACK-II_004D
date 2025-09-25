package cl.nexbyte.controller;

import cl.nexbyte.api.dto.CarritoSyncRequest;
import cl.nexbyte.api.dto.CarritoSyncResponse;
import cl.nexbyte.service.CarritoPersistService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
public class CarritoUsuarioController {

  private final CarritoPersistService service;

  public CarritoUsuarioController(CarritoPersistService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<CarritoSyncResponse> get(Authentication auth) {
    return ResponseEntity.ok(service.getNormalized(auth));
  }

  @PutMapping
  public ResponseEntity<CarritoSyncResponse> replace(Authentication auth,
                                                     @Valid @RequestBody CarritoSyncRequest req) {
    return ResponseEntity.ok(service.replace(auth, req));
  }

  @DeleteMapping
  public ResponseEntity<Void> clear(Authentication auth) {
    service.clear(auth);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/items/{idProducto}")
  public ResponseEntity<Void> remove(Authentication auth, @PathVariable Long idProducto) {
    service.removeItem(auth, idProducto);
    return ResponseEntity.noContent().build();
  }
}
