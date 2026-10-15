package api.dto;
import java.math.BigDecimal;
import java.util.List;
public record PedidoResponse(Long id, BigDecimal valorTotal, List<IngressoResponse> ingressos) {}
