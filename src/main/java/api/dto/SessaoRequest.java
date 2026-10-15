package api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

<<<<<<< HEAD
/** Componente do sistema de ingressos de cinema. */
public record SessaoRequest(@NotNull Long filmeId, @NotNull Long salaId, @NotNull LocalDateTime inicio, @NotNull
@DecimalMin("0.01") BigDecimal precoInteira) {
}
=======
public record SessaoRequest(@NotNull Long filmeId,@NotNull Long salaId,@NotNull LocalDateTime inicio,@NotNull @DecimalMin("0.01") BigDecimal precoInteira) {}
>>>>>>> e67d45c8d29cad88b0a5b4c4a3ccbbf854ec651a
