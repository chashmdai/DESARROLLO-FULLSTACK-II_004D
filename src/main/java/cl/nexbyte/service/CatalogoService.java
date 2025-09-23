package cl.nexbyte.service;

import cl.nexbyte.model.ProductoImagen;
import cl.nexbyte.repository.CategoriaRepository;
import cl.nexbyte.repository.EtiquetaRepository;
import cl.nexbyte.repository.MarcaRepository;
import cl.nexbyte.repository.ProductoImagenRepository;
import cl.nexbyte.repository.ProductoRepository;
import cl.nexbyte.repository.projection.CategoriaView;
import cl.nexbyte.repository.projection.MarcaView;
import cl.nexbyte.repository.projection.ProductoDetalleView;
import cl.nexbyte.repository.projection.ProductoPublicoView;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CatalogoService {
  private final CategoriaRepository categoriaRepository;
  private final MarcaRepository marcaRepository;
  private final ProductoRepository productoRepository;
  private final ProductoImagenRepository productoImagenRepository;
  private final EtiquetaRepository etiquetaRepository;

  public CatalogoService(CategoriaRepository categoriaRepository, MarcaRepository marcaRepository,
                         ProductoRepository productoRepository, ProductoImagenRepository productoImagenRepository,
                         EtiquetaRepository etiquetaRepository) {
    this.categoriaRepository = categoriaRepository;
    this.marcaRepository = marcaRepository;
    this.productoRepository = productoRepository;
    this.productoImagenRepository = productoImagenRepository;
    this.etiquetaRepository = etiquetaRepository;
  }

  public List<CategoriaView> categorias() { return categoriaRepository.listar(); }

  public List<MarcaView> marcas(String q) { return marcaRepository.listar(q == null || q.isBlank() ? null : q); }

  public Page<ProductoPublicoView> productos(String q, Long idCategoria, Long idMarca, boolean soloDisponibles, Pageable pageable) {
    String query = q == null || q.isBlank() ? null : q;
    return productoRepository.buscar(query, idCategoria, idMarca, soloDisponibles, pageable);
  }

  public Optional<Map<String,Object>> detallePorId(Long id) {
    Optional<ProductoDetalleView> base = productoRepository.detallePorId(id);
    if (base.isEmpty()) return Optional.empty();
    List<ProductoImagen> imagenes = productoImagenRepository.findByIdProductoOrderByEsPrincipalDescOrdenAscIdAsc(base.get().getId());
    List<String> etiquetas = etiquetaRepository.etiquetasDeProducto(base.get().getId());
    return Optional.of(Map.of(
      "producto", base.get(),
      "imagenes", imagenes,
      "etiquetas", etiquetas
    ));
  }

  public Optional<Map<String,Object>> detallePorSlug(String slug) {
    Optional<ProductoDetalleView> base = productoRepository.detallePorSlug(slug);
    if (base.isEmpty()) return Optional.empty();
    List<ProductoImagen> imagenes = productoImagenRepository.findByIdProductoOrderByEsPrincipalDescOrdenAscIdAsc(base.get().getId());
    List<String> etiquetas = etiquetaRepository.etiquetasDeProducto(base.get().getId());
    return Optional.of(Map.of(
      "producto", base.get(),
      "imagenes", imagenes,
      "etiquetas", etiquetas
    ));
  }
}
