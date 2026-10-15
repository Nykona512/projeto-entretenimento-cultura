package api.dto;

import jakarta.validation.constraints.*;

/** Componente do sistema de ingressos de cinema. */
public record FilmeRequest(@NotBlank String titulo, @NotNull
@Min(1) Integer duracaoMinutos) {
}
