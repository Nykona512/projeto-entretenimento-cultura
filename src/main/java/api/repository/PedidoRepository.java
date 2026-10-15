package api.repository;

import api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

/** Componente do sistema de ingressos de cinema. */
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
