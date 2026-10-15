package api.dto;
<<<<<<< HEAD

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

/** Componente do sistema de ingressos de cinema. */
public record PedidoRequest(@NotNull Long sessaoId, @NotEmpty List<@NotNull
@Valid ItemIngressoRequest> ingressos) {
}
=======
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
public record PedidoRequest(@NotNull Long sessaoId, @NotEmpty List<@NotNull @Valid ItemIngressoRequest> ingressos) {}
>>>>>>> e67d45c8d29cad88b0a5b4c4a3ccbbf854ec651a
