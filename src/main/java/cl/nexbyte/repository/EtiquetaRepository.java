package cl.nexbyte.repository;

import cl.nexbyte.model.Etiqueta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long> {
  @Query(value = """
    SELECT e.nombre
    FROM producto_etiqueta pe
    JOIN etiqueta e ON e.id = pe.id_etiqueta
    WHERE pe.id_producto = :idProducto
    ORDER BY e.nombre ASC
    """, nativeQuery = true)
  List<String> etiquetasDeProducto(@Param("idProducto") Long idProducto);
}
