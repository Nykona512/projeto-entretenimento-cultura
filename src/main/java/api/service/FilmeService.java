package api.service;
import api.model.Filme; import api.repository.FilmeRepository; import api.dto.FilmeRequest;
import java.util.List; import org.springframework.stereotype.Service;

@Service public class FilmeService {

 private final FilmeRepository repo; public FilmeService(FilmeRepository repo){this.repo=repo;}

 public Filme criar(FilmeRequest d){return repo.save(new Filme(d.titulo(),d.duracaoMinutos()));}
 
 public List<Filme> listar(){return repo.findAll();}
 
 public Filme buscar(Long id){return repo.findById(id).orElseThrow(()->new RecursoNaoEncontradoException("Filme não encontrado: "+id));}
}
