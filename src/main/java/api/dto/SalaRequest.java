package api.dto;
import jakarta.validation.constraints.*;

public record SalaRequest(@NotBlank String nome, @NotNull @Min(1) Integer capacidade) {}
