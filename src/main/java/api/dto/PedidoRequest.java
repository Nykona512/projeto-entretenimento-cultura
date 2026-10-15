package api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

/** Componente do sistema de ingressos de cinema. */
public record PedidoRequest(@NotNull Long sessaoId, @NotEmpty List<@NotNull
@Valid ItemIngressoRequest> ingressos) {
}
