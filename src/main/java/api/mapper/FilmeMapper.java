package api.mapper;

import api.dto.FilmeResponse;
import api.model.Filme;
import org.springframework.stereotype.Component;

/** Converte entidades Filme em DTOs de resposta. */
@Component
public class FilmeMapper {
    public FilmeResponse paraResponse(Filme f) {
        return new FilmeResponse(f.id, f.titulo, f.duracaoMinutos);
    }
}
