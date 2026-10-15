package api.controller;

import api.dto.*;
import api.service.SessaoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** Expõe os endpoints REST de sessao. */
@RestController
@RequestMapping("/sessoes")
public class SessaoController {
    private final SessaoService service;
    public SessaoController(SessaoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessaoResponse criar(@Valid @RequestBody SessaoRequest dados) {
        return service.criar(dados);
    }

    @GetMapping
    public List<SessaoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public SessaoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }
}
