package cl.nexbyte.controller;

import cl.nexbyte.model.Comuna;
import cl.nexbyte.model.Region;
import cl.nexbyte.repository.ComunaRepository;
import cl.nexbyte.repository.RegionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GeoController {
  private final RegionRepository regionRepo;
  private final ComunaRepository comunaRepo;

  public GeoController(RegionRepository regionRepo, ComunaRepository comunaRepo) {
    this.regionRepo = regionRepo; this.comunaRepo = comunaRepo;
  }

  @GetMapping("/regiones")
  public ResponseEntity<List<Region>> regiones() {
    return ResponseEntity.ok(regionRepo.findAllByOrderByOrdenAscNombreAsc());
  }

  @GetMapping("/regiones/{idRegion}/comunas")
  public ResponseEntity<List<Comuna>> comunas(@PathVariable Long idRegion) {
    return ResponseEntity.ok(comunaRepo.findByIdRegionOrderByOrdenAscNombreAsc(idRegion));
  }
}
