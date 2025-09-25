package cl.nexbyte.repository;

import cl.nexbyte.model.ProductoImagen;
import cl.nexbyte.repository.projection.ImagenPrincipalRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductoImagenLiteRepository extends JpaRepository<ProductoImagen, Long> {
  @Query(value = """
      SELECT id_producto AS idProducto, url AS url
      FROM producto_imagen
      WHERE es_principal = TRUE AND id_producto IN (:ids)
      ORDER BY orden ASC
      """, nativeQuery = true)
  List<ImagenPrincipalRow> findPrincipalUrls(Collection<Long> ids);
}
