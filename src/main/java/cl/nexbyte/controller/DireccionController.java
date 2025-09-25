package cl.nexbyte.controller;

import cl.nexbyte.api.dto.DireccionRequest;
import cl.nexbyte.model.Direccion;
import cl.nexbyte.model.Usuario;
import cl.nexbyte.repository.ComunaRepository;
import cl.nexbyte.repository.DireccionRepository;
import cl.nexbyte.repository.RegionRepository;
import cl.nexbyte.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {
  private final DireccionRepository repo;
  private final UsuarioRepository usuarioRepo;
  private final RegionRepository regionRepo;
  private final ComunaRepository comunaRepo;

  public DireccionController(DireccionRepository repo, UsuarioRepository usuarioRepo,
                             RegionRepository regionRepo, ComunaRepository comunaRepo) {
    this.repo = repo; this.usuarioRepo = usuarioRepo; this.regionRepo = regionRepo; this.comunaRepo = comunaRepo;
  }

  @GetMapping
  public ResponseEntity<List<Direccion>> listar(Authentication auth) {
    Long userId = usuarioRepo.findByEmail(auth.getName()).map(Usuario::getId).orElseThrow();
    return ResponseEntity.ok(repo.findByIdUsuarioOrderByEsPredeterminadaDescIdDesc(userId));
  }

  @PostMapping
  @Transactional
  public ResponseEntity<Direccion> crear(Authentication auth, @Valid @RequestBody DireccionRequest dto) {
    Long userId = usuarioRepo.findByEmail(auth.getName()).map(Usuario::getId).orElseThrow();
    validarGeo(dto.getIdRegion(), dto.getIdComuna());
    if (Boolean.TRUE.equals(dto.getEsPredeterminada())) repo.clearPredeterminadas(userId);

    Direccion d = mapToEntity(new Direccion(), userId, dto);
    repo.saveAndFlush(d);
    return ResponseEntity.created(URI.create("/api/direcciones/" + d.getId())).body(d);
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<Direccion> actualizar(Authentication auth, @PathVariable Long id, @Valid @RequestBody DireccionRequest dto) {
    Long userId = usuarioRepo.findByEmail(auth.getName()).map(Usuario::getId).orElseThrow();
    Direccion d = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Dirección no existe"));
    if (!d.getIdUsuario().equals(userId)) return ResponseEntity.status(403).build();

    validarGeo(dto.getIdRegion(), dto.getIdComuna());
    if (Boolean.TRUE.equals(dto.getEsPredeterminada())) repo.clearPredeterminadas(userId);

    mapToEntity(d, userId, dto);
    repo.save(d);
    return ResponseEntity.ok(d);
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Void> eliminar(Authentication auth, @PathVariable Long id) {
    Long userId = usuarioRepo.findByEmail(auth.getName()).map(Usuario::getId).orElseThrow();
    Direccion d = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Dirección no existe"));
    if (!d.getIdUsuario().equals(userId)) return ResponseEntity.status(403).build();
    repo.delete(d);
    return ResponseEntity.noContent().build();
  }

  private void validarGeo(Long idRegion, Long idComuna) {
    if (!regionRepo.existsById(idRegion)) throw new IllegalArgumentException("Región inválida");
    if (!comunaRepo.existsById(idComuna) || !comunaRepo.existsByIdAndIdRegion(idComuna, idRegion))
      throw new IllegalArgumentException("Comuna inválida para la región");
  }

  private Direccion mapToEntity(Direccion d, Long userId, DireccionRequest dto) {
    d.setIdUsuario(userId);
    d.setAlias(dto.getAlias());
    d.setNombreReceptor(dto.getNombreReceptor());
    d.setTelefono(dto.getTelefono());
    d.setIdRegion(dto.getIdRegion());
    d.setIdComuna(dto.getIdComuna());
    d.setCalle(dto.getCalle());
    d.setNumero(dto.getNumero());
    d.setDepto(dto.getDepto());
    d.setCodigoPostal(dto.getCodigoPostal());
    d.setNotas(dto.getNotas());
    d.setEsPredeterminada(Boolean.TRUE.equals(dto.getEsPredeterminada()));
    return d;
  }
}
