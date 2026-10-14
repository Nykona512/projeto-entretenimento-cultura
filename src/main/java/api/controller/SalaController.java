package api.controller;
import api.model.Sala; import api.dto.SalaRequest; import api.service.SalaService;
import jakarta.validation.Valid; import java.util.List;
import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/salas") public class SalaController {

 private final SalaService service; public SalaController(SalaService service){this.service=service;}

 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Sala criar(@Valid @RequestBody SalaRequest dados){return service.criar(dados);}

 @GetMapping public List<Sala> listar(){return service.listar();}
 
 @GetMapping("/{id}") public Sala buscar(@PathVariable Long id){return service.buscar(id);}
}
