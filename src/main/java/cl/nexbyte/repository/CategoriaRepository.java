package cl.nexbyte.repository;

import cl.nexbyte.model.Categoria;
import cl.nexbyte.repository.projection.CategoriaView;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
  @Query(value = """
    SELECT c.id AS id, c.nombre AS nombre, c.slug AS slug,
           c.id_padre AS idPadre, c.visible AS visible, c.orden AS orden
    FROM categoria c
    ORDER BY c.orden ASC, c.nombre ASC
    """, nativeQuery = true)
  List<CategoriaView> listar();
}
