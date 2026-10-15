package api.mapper;

import api.dto.SalaResponse;
import api.model.Sala;
import org.springframework.stereotype.Component;

/** Converte entidades Sala em DTOs de resposta. */
@Component
public class SalaMapper {
    public SalaResponse paraResponse(Sala s) {
        return new SalaResponse(s.id, s.nome, s.capacidade);
    }
}
