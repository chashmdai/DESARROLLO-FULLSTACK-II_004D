package cl.nexbyte.repository.projection;

public interface CategoriaView {
  Long getId();
  String getNombre();
  String getSlug();
  Long getIdPadre();
  Boolean getVisible();
  Integer getOrden();
}
