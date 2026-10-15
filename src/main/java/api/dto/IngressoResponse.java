package api.dto;

import api.model.TipoIngresso;
import java.math.BigDecimal;

/** Dados de um ingresso emitido, sem expor a entidade JPA. */
public record IngressoResponse(Long id, Long sessaoId, Long pedidoId, TipoIngresso tipo, BigDecimal valor) {
}
