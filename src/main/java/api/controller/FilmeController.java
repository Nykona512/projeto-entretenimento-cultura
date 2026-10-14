package api.controller;
import api.model.Filme; import api.dto.FilmeRequest; import api.service.FilmeService;
import jakarta.validation.Valid; import java.util.List;
import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/filmes") public class FilmeController {

 private final FilmeService service; public FilmeController(FilmeService service){this.service=service;}

 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Filme criar(@Valid @RequestBody FilmeRequest dados){return service.criar(dados);}

 @GetMapping public List<Filme> listar(){return service.listar();}
 
 @GetMapping("/{id}") public Filme buscar(@PathVariable Long id){return service.buscar(id);}
}
