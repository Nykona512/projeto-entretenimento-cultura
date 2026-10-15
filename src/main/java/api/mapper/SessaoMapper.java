package api.mapper;

import api.dto.SessaoResponse;
import api.model.Sessao;
import org.springframework.stereotype.Component;

/** Converte entidades Sessao em DTOs de resposta. */
@Component
public class SessaoMapper {
    public SessaoResponse paraResponse(Sessao s) {
        return new SessaoResponse(s.id, s.filme.id, s.sala.id, s.inicio, s.precoInteira);
    }
}
