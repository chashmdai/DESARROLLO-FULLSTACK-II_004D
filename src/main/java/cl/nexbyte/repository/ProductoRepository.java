package cl.nexbyte.repository;

import cl.nexbyte.model.Producto;
import cl.nexbyte.repository.projection.ProductoDetalleView;
import cl.nexbyte.repository.projection.ProductoPublicoView;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
  @Query(value = """
    SELECT
      p.id AS id,
      p.nombre AS nombre,
      p.slug AS slug,
      p.sku AS sku,
      p.id_categoria AS idCategoria,
      p.id_marca AS idMarca,
      p.stock_actual AS stockActual,
      p.publicado_en AS publicadoEn,
      p.precio_vigente AS precioVigente,
      (SELECT pi.url FROM producto_imagen pi
         WHERE pi.id_producto = p.id
         ORDER BY pi.es_principal DESC, pi.orden ASC, pi.id ASC
         LIMIT 1) AS imagenUrl
    FROM vw_producto_publico p
    WHERE (:idCategoria IS NULL OR p.id_categoria = :idCategoria)
      AND (:idMarca IS NULL OR p.id_marca = :idMarca)
      AND (:soloDisponibles = FALSE OR p.stock_actual > 0)
      AND (:q IS NULL OR p.nombre LIKE CONCAT('%',:q,'%') OR p.sku LIKE CONCAT('%',:q,'%'))
    ORDER BY p.publicado_en DESC, p.nombre ASC
    """,
    countQuery = """
      SELECT COUNT(*)
      FROM vw_producto_publico p
      WHERE (:idCategoria IS NULL OR p.id_categoria = :idCategoria)
        AND (:idMarca IS NULL OR p.id_marca = :idMarca)
        AND (:soloDisponibles = FALSE OR p.stock_actual > 0)
        AND (:q IS NULL OR p.nombre LIKE CONCAT('%',:q,'%') OR p.sku LIKE CONCAT('%',:q,'%'))
    """,
    nativeQuery = true)
  Page<ProductoPublicoView> buscar(
    @Param("q") String q,
    @Param("idCategoria") Long idCategoria,
    @Param("idMarca") Long idMarca,
    @Param("soloDisponibles") boolean soloDisponibles,
    Pageable pageable
  );

  @Query(value = """
    SELECT
      pr.id AS id,
      pr.nombre AS nombre,
      pr.slug AS slug,
      pr.sku AS sku,
      pr.id_categoria AS idCategoria,
      pr.id_marca AS idMarca,
      pr.stock_actual AS stockActual,
      pr.garantia_meses AS garantiaMeses,
      pr.descripcion_larga AS descripcionLarga,
      pr.peso_gramos AS pesoGramos,
      pr.dimensiones AS dimensiones,
      pr.publicado_en AS publicadoEn,
      v.precio_vigente AS precioVigente,
      pr.precio_lista AS precioLista,
      pr.precio_oferta AS precioOferta,
      pr.oferta_inicio AS ofertaInicio,
      pr.oferta_fin AS ofertaFin
    FROM producto pr
    JOIN vw_producto_publico v ON v.id = pr.id
    WHERE pr.id = :id
    LIMIT 1
    """, nativeQuery = true)
  Optional<ProductoDetalleView> detallePorId(@Param("id") Long id);

  @Query(value = """
    SELECT
      pr.id AS id,
      pr.nombre AS nombre,
      pr.slug AS slug,
      pr.sku AS sku,
      pr.id_categoria AS idCategoria,
      pr.id_marca AS idMarca,
      pr.stock_actual AS stockActual,
      pr.garantia_meses AS garantiaMeses,
      pr.descripcion_larga AS descripcionLarga,
      pr.peso_gramos AS pesoGramos,
      pr.dimensiones AS dimensiones,
      pr.publicado_en AS publicadoEn,
      v.precio_vigente AS precioVigente,
      pr.precio_lista AS precioLista,
      pr.precio_oferta AS precioOferta,
      pr.oferta_inicio AS ofertaInicio,
      pr.oferta_fin AS ofertaFin
    FROM producto pr
    JOIN vw_producto_publico v ON v.id = pr.id
    WHERE pr.slug = :slug
    LIMIT 1
    """, nativeQuery = true)
  Optional<ProductoDetalleView> detallePorSlug(@Param("slug") String slug);
}
