package api.dto;
<<<<<<< HEAD

import api.model.TipoIngresso;
import java.math.BigDecimal;

/** Dados de um ingresso emitido, sem expor a entidade JPA. */
public record IngressoResponse(Long id, Long sessaoId, Long pedidoId, TipoIngresso tipo, BigDecimal valor) {
=======
import api.model.*;
import java.math.BigDecimal;
public record IngressoResponse(Long id, Long sessaoId, Long pedidoId, TipoIngresso tipo, BigDecimal valor) {
 public static IngressoResponse de(Ingresso i){return new IngressoResponse(i.id,i.sessao.id,i.pedido.id,i.tipo,i.valor);}
>>>>>>> e67d45c8d29cad88b0a5b4c4a3ccbbf854ec651a
}
