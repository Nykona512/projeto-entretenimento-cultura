package api.dto;
import api.model.*;
import java.math.BigDecimal;
public record IngressoResponse(Long id, Long sessaoId, Long pedidoId, TipoIngresso tipo, BigDecimal valor) {
 public static IngressoResponse de(Ingresso i){return new IngressoResponse(i.id,i.sessao.id,i.pedido.id,i.tipo,i.valor);}
}
