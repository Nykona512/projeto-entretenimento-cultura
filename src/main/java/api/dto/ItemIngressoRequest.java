package api.dto;
<<<<<<< HEAD

import api.model.TipoIngresso;
import jakarta.validation.constraints.NotNull;

/** Componente do sistema de ingressos de cinema. */
public record ItemIngressoRequest(@NotNull TipoIngresso tipo) {
}
=======
import api.model.TipoIngresso;
import jakarta.validation.constraints.NotNull;
public record ItemIngressoRequest(@NotNull TipoIngresso tipo) {}
>>>>>>> e67d45c8d29cad88b0a5b4c4a3ccbbf854ec651a
