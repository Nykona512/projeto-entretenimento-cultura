package api.dto;

import jakarta.validation.constraints.*;

/** Componente do sistema de ingressos de cinema. */
public record SalaRequest(@NotBlank String nome, @NotNull
@Min(1) Integer capacidade) {
}
