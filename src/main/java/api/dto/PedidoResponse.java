package api.dto;
<<<<<<< HEAD

import java.math.BigDecimal;
import java.util.List;

/** Componente do sistema de ingressos de cinema. */
public record PedidoResponse(Long id, BigDecimal valorTotal, List<IngressoResponse> ingressos) {
}
=======
import java.math.BigDecimal;
import java.util.List;
public record PedidoResponse(Long id, BigDecimal valorTotal, List<IngressoResponse> ingressos) {}
>>>>>>> e67d45c8d29cad88b0a5b4c4a3ccbbf854ec651a
