package api.controller;

import api.dto.IngressoResponse;
import api.service.IngressoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/** Consulta ingressos; a emissão é feita pelo POST /pedidos. */
@RestController
@RequestMapping("/ingressos")
public class IngressoController {
    private final IngressoService service;
    public IngressoController(IngressoService service) {
        this.service = service;
    }

    @GetMapping
    public List<IngressoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public IngressoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }
}
