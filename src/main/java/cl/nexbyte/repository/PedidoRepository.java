package cl.nexbyte.repository;

import cl.nexbyte.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
  Page<Pedido> findByIdUsuarioOrderByCreadoEnDesc(Long idUsuario, Pageable pageable);
}
