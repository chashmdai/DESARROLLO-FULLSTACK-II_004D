package cl.nexbyte.controller;

import cl.nexbyte.repository.projection.CategoriaView;
import cl.nexbyte.repository.projection.MarcaView;
import cl.nexbyte.repository.projection.ProductoPublicoView;
import cl.nexbyte.service.CatalogoService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CatalogoController {
  private final CatalogoService service;

  public CatalogoController(CatalogoService service) { this.service = service; }

  @GetMapping("/categorias")
  public ResponseEntity<List<CategoriaView>> categorias() {
    return ResponseEntity.ok(service.categorias());
  }

  @GetMapping("/marcas")
  public ResponseEntity<List<MarcaView>> marcas(@RequestParam(required = false) String q) {
    return ResponseEntity.ok(service.marcas(q));
  }

  @GetMapping("/productos")
  public ResponseEntity<Page<ProductoPublicoView>> productos(
      @RequestParam(required = false) String q,
      @RequestParam(required = false) Long idCategoria,
      @RequestParam(required = false) Long idMarca,
      @RequestParam(defaultValue = "false") boolean soloDisponibles,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "12") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(service.productos(q, idCategoria, idMarca, soloDisponibles, pageable));
  }

  @GetMapping("/productos/{id}")
  public ResponseEntity<Map<String,Object>> productoPorId(@PathVariable Long id) {
    Optional<Map<String,Object>> r = service.detallePorId(id);
    return r.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/productos/slug/{slug}")
  public ResponseEntity<Map<String,Object>> productoPorSlug(@PathVariable String slug) {
    Optional<Map<String,Object>> r = service.detallePorSlug(slug);
    return r.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
