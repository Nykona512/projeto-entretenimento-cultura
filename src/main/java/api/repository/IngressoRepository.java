package api.repository;

import api.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/** Componente do sistema de ingressos de cinema. */
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
    long countBySessaoId(Long sessaoId);
    long countBySessaoIdAndTipo(Long sessaoId, TipoIngresso tipo);
    List<Ingresso> findByPedidoId(Long pedidoId);
}
