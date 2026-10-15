package api.controller;
<<<<<<< HEAD

import api.dto.*;
import api.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
/** Componente do sistema de ingressos de cinema. */
public class PedidoController {
    private final PedidoService service;
    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse criar(@Valid @RequestBody PedidoRequest dados) {
        return service.criar(dados);
    }

    @GetMapping("/{id}")
    public PedidoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }
=======
import api.dto.*; import api.service.PedidoService;
import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/pedidos") public class PedidoController {
 private final PedidoService service; public PedidoController(PedidoService service){this.service=service;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public PedidoResponse criar(@Valid @RequestBody PedidoRequest dados){return service.criar(dados);}
 @GetMapping("/{id}") public PedidoResponse buscar(@PathVariable Long id){return service.buscar(id);}
>>>>>>> e67d45c8d29cad88b0a5b4c4a3ccbbf854ec651a
}
