package api.service;
import api.model.Sala; import api.repository.SalaRepository; import api.dto.SalaRequest;
import java.util.List; import org.springframework.stereotype.Service;
@Service public class SalaService {
 private final SalaRepository repo; public SalaService(SalaRepository repo){this.repo=repo;}
 public Sala criar(SalaRequest d){return repo.save(new Sala(d.nome(),d.capacidade()));}
 public List<Sala> listar(){return repo.findAll();}
 public Sala buscar(Long id){return repo.findById(id).orElseThrow(()->new RecursoNaoEncontradoException("Sala não encontrada: "+id));}
}
