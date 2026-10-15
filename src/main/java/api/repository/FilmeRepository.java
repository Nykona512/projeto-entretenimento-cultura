package api.repository;

import api.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

/** Componente do sistema de ingressos de cinema. */
public interface FilmeRepository extends JpaRepository<Filme, Long> {
}
