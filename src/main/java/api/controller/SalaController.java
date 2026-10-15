package api.controller;

import api.dto.*;
import api.service.SalaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** Expõe os endpoints REST de sala. */
@RestController
@RequestMapping("/salas")
public class SalaController {
    private final SalaService service;
    public SalaController(SalaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalaResponse criar(@Valid @RequestBody SalaRequest dados) {
        return service.criar(dados);
    }

    @GetMapping
    public List<SalaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public SalaResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }
}
