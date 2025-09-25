package cl.nexbyte.controller;

import cl.nexbyte.api.dto.CarritoSyncRequest;
import cl.nexbyte.api.dto.CarritoSyncResponse;
import cl.nexbyte.service.CarritoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {
  private final CarritoService service;
  public CarritoController(CarritoService service) { this.service = service; }

  @PostMapping("/sync")
  public ResponseEntity<CarritoSyncResponse> sync(@Valid @RequestBody CarritoSyncRequest req) {
    return ResponseEntity.ok(service.sync(req));
  }
}
