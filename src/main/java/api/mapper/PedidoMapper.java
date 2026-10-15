package api.mapper;

import api.dto.PedidoResponse;
import api.dto.IngressoResponse;
import api.model.Pedido;
import java.util.List;
import org.springframework.stereotype.Component;

/** Monta o DTO de um pedido e seus ingressos. */
@Component
public class PedidoMapper {
    public PedidoResponse paraResponse(Pedido pedido, List<IngressoResponse> ingressos) {
        return new PedidoResponse(pedido.id, pedido.valorTotal, ingressos);
    }
}
