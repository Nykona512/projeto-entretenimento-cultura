package api.dto;
import api.model.TipoIngresso;
import jakarta.validation.constraints.NotNull;
public record ItemIngressoRequest(@NotNull TipoIngresso tipo) {}
