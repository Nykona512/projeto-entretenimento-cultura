package api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.dto.SessaoRequest;
import api.model.Filme;
import api.model.Sala;
import api.model.Sessao;
import api.repository.SessaoRepository;

@Service public class SessaoService {

    private final SessaoRepository repo;
    private final FilmeService filmes; 
    private final SalaService salas;

    public SessaoService(SessaoRepository repo,FilmeService filmes,SalaService salas){this.repo=repo;this.filmes=filmes;this.salas=salas;}

    @Transactional 
    public Sessao criar(SessaoRequest d){
        Filme filme=filmes.buscar(d.filmeId()); Sala sala=salas.buscar(d.salaId());
        LocalDateTime novoFim=d.inicio().plusMinutes(filme.duracaoMinutos+20);
        // R2: cada intervalo inclui 20 minutos extras de limpeza.
        for(Sessao existente:repo.findBySalaId(sala.id)){
            LocalDateTime fimExistente=existente.inicio.plusMinutes(existente.filme.duracaoMinutos+20);
            if(d.inicio().isBefore(fimExistente) && existente.inicio.isBefore(novoFim))
                throw new RegraDeNegocioException("R2: O horário informado apresenta conflito com outra sessão nesta sala.");
        }
        return repo.save(new Sessao(filme,sala,d.inicio(),d.precoInteira()));
    }
    public List<Sessao> listar(){return repo.findAll();}
    public Sessao buscar(Long id){return repo.findById(id).orElseThrow(()->new RecursoNaoEncontradoException("Sessão não encontrada: "+id));}
}
