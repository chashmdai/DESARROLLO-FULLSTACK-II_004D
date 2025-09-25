package cl.nexbyte.repository;

import cl.nexbyte.model.Comuna;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComunaRepository extends JpaRepository<Comuna, Long> {
  List<Comuna> findByIdRegionOrderByOrdenAscNombreAsc(Long idRegion);
  boolean existsByIdAndIdRegion(Long id, Long idRegion);
}
