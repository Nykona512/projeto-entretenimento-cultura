package api.repository;

import api.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

/** Componente do sistema de ingressos de cinema. */
public interface SalaRepository extends JpaRepository<Sala, Long> {
}
