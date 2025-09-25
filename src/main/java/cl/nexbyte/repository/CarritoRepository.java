package cl.nexbyte.repository;

import cl.nexbyte.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
  Optional<Carrito> findByIdUsuario(Long idUsuario);
  boolean existsByIdUsuario(Long idUsuario);
}
