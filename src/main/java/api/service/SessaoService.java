package api.service;

import api.dto.*;
import api.mapper.SessaoMapper;
import api.model.*;
import api.repository.SessaoRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Organiza sessões, impedindo horários sobrepostos na mesma sala (R2). */
@Service
public class SessaoService {
    private final SessaoRepository repo;
    private final FilmeService filmes;
    private final SalaService salas;
    private final SessaoMapper mapper;
    public SessaoService(SessaoRepository repo, FilmeService filmes, SalaService salas, SessaoMapper mapper) {
        this.repo = repo;
        this.filmes = filmes;
        this.salas = salas;
        this.mapper = mapper;
    }

    @Transactional
    public SessaoResponse criar(SessaoRequest d) {
        Filme filme = filmes.buscarEntidade(d.filmeId());
        Sala sala = salas.buscarEntidade(d.salaId());
        LocalDateTime novoFim = d.inicio().plusMinutes(filme.duracaoMinutos + 20);
        for (Sessao existente: repo.findBySalaId(sala.id)) {
            LocalDateTime fim = existente.inicio.plusMinutes(existente.filme.duracaoMinutos + 20);
            if (d.inicio().isBefore(fim)&& existente.inicio.isBefore(novoFim)) throw new RegraDeNegocioException("R2: O horário informado apresenta conflito com outra sessão nesta sala.");
        }
        return mapper.paraResponse(repo.save(new Sessao(filme, sala, d.inicio(), d.precoInteira())));
    }

    @Transactional(readOnly = true)
    public List<SessaoResponse> listar() {
        return repo.findAll().stream().map(mapper::paraResponse).toList();
    }

    @Transactional(readOnly = true)
    public SessaoResponse buscar(Long id) {
        return mapper.paraResponse(repo.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Sessão não encontrada: " + id)));
    }
}
