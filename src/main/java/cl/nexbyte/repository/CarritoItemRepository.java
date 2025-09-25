package cl.nexbyte.repository;

import cl.nexbyte.model.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {

  List<CarritoItem> findByIdCarritoOrderByIdDesc(Long idCarrito);

  Optional<CarritoItem> findByIdCarritoAndIdProducto(Long idCarrito, Long idProducto);

  @Modifying
  @Transactional
  void deleteByIdCarrito(Long idCarrito);

  @Modifying
  @Transactional
  void deleteByIdCarritoAndIdProducto(Long idCarrito, Long idProducto);
}
