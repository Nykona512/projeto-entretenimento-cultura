package api.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

/** Dados públicos devolvidos nas consultas e cadastros de sessao. */
public record SessaoResponse(Long id, Long filmeId, Long salaId, LocalDateTime inicio, BigDecimal precoInteira) {
}
