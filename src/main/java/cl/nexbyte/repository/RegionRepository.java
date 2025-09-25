package cl.nexbyte.repository;

import cl.nexbyte.model.Region;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
  List<Region> findAllByOrderByOrdenAscNombreAsc();
}
