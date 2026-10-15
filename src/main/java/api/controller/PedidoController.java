package api.controller;
import api.dto.*; import api.service.PedidoService;
import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/pedidos") public class PedidoController {
 private final PedidoService service; public PedidoController(PedidoService service){this.service=service;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public PedidoResponse criar(@Valid @RequestBody PedidoRequest dados){return service.criar(dados);}
 @GetMapping("/{id}") public PedidoResponse buscar(@PathVariable Long id){return service.buscar(id);}
}
