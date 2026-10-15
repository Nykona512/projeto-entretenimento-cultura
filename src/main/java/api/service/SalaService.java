package api.service;

import api.dto.*;
import api.mapper.SalaMapper;
import api.model.Sala;
import api.repository.SalaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Cadastra e consulta salas e devolve DTOs. */
@Service
public class SalaService {
    private final SalaRepository repo;
    private final SalaMapper mapper;
    public SalaService(SalaRepository repo, SalaMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public SalaResponse criar(SalaRequest d) {
        return mapper.paraResponse(repo.save(new Sala(d.nome(), d.capacidade())));
    }

    public List<SalaResponse> listar() {
        return repo.findAll().stream().map(mapper::paraResponse).toList();
    }

    public SalaResponse buscar(Long id) {
        return mapper.paraResponse(buscarEntidade(id));
    }

    public Sala buscarEntidade(Long id) {
        return repo.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Sala não encontrada: " + id));
    }
}
