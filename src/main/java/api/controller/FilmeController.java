package api.controller;

import api.dto.*;
import api.service.FilmeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** Expõe os endpoints REST de filme. */
@RestController
@RequestMapping("/filmes")
public class FilmeController {
    private final FilmeService service;
    public FilmeController(FilmeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmeResponse criar(@Valid @RequestBody FilmeRequest dados) {
        return service.criar(dados);
    }

    @GetMapping
    public List<FilmeResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public FilmeResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }
}
