package api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api.model.Sessao;
import jakarta.persistence.LockModeType;

public interface SessaoRepository extends JpaRepository<Sessao,Long> {
    List<Sessao> findBySalaId(Long salaId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Sessao s where s.id = :id")
    java.util.Optional<Sessao> buscarParaVenda(@Param("id") Long id);
}
