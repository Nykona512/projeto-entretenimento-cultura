package api.dto;

import api.model.TipoIngresso;
import jakarta.validation.constraints.NotNull;

/** Componente do sistema de ingressos de cinema. */
public record ItemIngressoRequest(@NotNull TipoIngresso tipo) {
}
