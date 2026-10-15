package api.mapper;

import api.dto.IngressoResponse;
import api.model.Ingresso;
import org.springframework.stereotype.Component;

/** Converte ingressos persistidos em dados de resposta. */
@Component
public class IngressoMapper {
    public IngressoResponse paraResponse(Ingresso i) {
        return new IngressoResponse(i.id, i.sessao.id, i.pedido.id, i.tipo, i.valor);
    }
}
