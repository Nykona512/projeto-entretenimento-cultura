package api.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import api.dto.SessaoRequest;
import api.model.Sessao;
import api.service.SessaoService;
import jakarta.validation.Valid;

@RestController @RequestMapping("/sessoes") 
public class SessaoController {
    private final SessaoService service; public SessaoController(SessaoService service){this.service=service;}

    @PostMapping @ResponseStatus(HttpStatus.CREATED) 
    public Sessao criar(@Valid @RequestBody SessaoRequest dados){return service.criar(dados);}

    @GetMapping 
    public List<Sessao> listar(){return service.listar();}
    
    @GetMapping("/{id}") 
    public Sessao buscar(@PathVariable Long id){return service.buscar(id);}
}
