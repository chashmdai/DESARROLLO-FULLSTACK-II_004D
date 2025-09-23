package cl.nexbyte.repository;

import cl.nexbyte.model.ProductoImagen;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoImagenRepository extends JpaRepository<ProductoImagen, Long> {
  List<ProductoImagen> findByIdProductoOrderByEsPrincipalDescOrdenAscIdAsc(Long idProducto);
}
