package api.dto;

import java.math.BigDecimal;
import java.util.List;

/** Componente do sistema de ingressos de cinema. */
public record PedidoResponse(Long id, BigDecimal valorTotal, List<IngressoResponse> ingressos) {
}
