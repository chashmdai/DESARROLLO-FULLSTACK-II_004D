package cl.nexbyte.repository;

import cl.nexbyte.repository.projection.ProductoSyncView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductoSyncRepository extends JpaRepository<cl.nexbyte.model.Producto, Long> {
  @Query(value = """
      SELECT id AS id,
             sku AS sku,
             nombre AS nombre,
             slug AS slug,
             stock_actual AS stockActual,
             precio_vigente AS precioVigente
      FROM vw_producto_publico
      WHERE id IN (:ids)
      """, nativeQuery = true)
  List<ProductoSyncView> findPublicInfoByIds(Collection<Long> ids);
}
