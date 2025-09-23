package cl.nexbyte.repository;

import cl.nexbyte.model.Marca;
import cl.nexbyte.repository.projection.MarcaView;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
  @Query(value = """
    SELECT m.id AS id, m.nombre AS nombre, m.slug AS slug, m.logo_url AS logoUrl
    FROM marca m
    WHERE (:q IS NULL OR m.nombre LIKE CONCAT('%',:q,'%') OR m.slug LIKE CONCAT('%',:q,'%'))
    ORDER BY m.nombre ASC
    """, nativeQuery = true)
  List<MarcaView> listar(@Param("q") String q);
}
