package api.service;

import api.dto.*;
import api.mapper.FilmeMapper;
import api.model.Filme;
import api.repository.FilmeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Cadastra e consulta filmes e devolve DTOs. */
@Service
public class FilmeService {
    private final FilmeRepository repo;
    private final FilmeMapper mapper;
    public FilmeService(FilmeRepository repo, FilmeMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public FilmeResponse criar(FilmeRequest d) {
        return mapper.paraResponse(repo.save(new Filme(d.titulo(), d.duracaoMinutos())));
    }

    public List<FilmeResponse> listar() {
        return repo.findAll().stream().map(mapper::paraResponse).toList();
    }

    public FilmeResponse buscar(Long id) {
        return mapper.paraResponse(buscarEntidade(id));
    }

    public Filme buscarEntidade(Long id) {
        return repo.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Filme não encontrado: " + id));
    }
}
