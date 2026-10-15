package api.repository;
<<<<<<< HEAD

import api.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/** Componente do sistema de ingressos de cinema. */
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
    long countBySessaoId(Long sessaoId);
    long countBySessaoIdAndTipo(Long sessaoId, TipoIngresso tipo);
    List<Ingresso> findByPedidoId(Long pedidoId);
=======
import api.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface IngressoRepository extends JpaRepository<Ingresso,Long> {
 long countBySessaoId(Long sessaoId);
 long countBySessaoIdAndTipo(Long sessaoId, TipoIngresso tipo);
 List<Ingresso> findByPedidoId(Long pedidoId);
>>>>>>> e67d45c8d29cad88b0a5b4c4a3ccbbf854ec651a
}
