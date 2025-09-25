package cl.nexbyte.repository;

import cl.nexbyte.model.Direccion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {
  List<Direccion> findByIdUsuarioOrderByEsPredeterminadaDescIdDesc(Long idUsuario);

  @Modifying
  @Query("update Direccion d set d.esPredeterminada=false where d.idUsuario=?1")
  int clearPredeterminadas(Long idUsuario);
}
