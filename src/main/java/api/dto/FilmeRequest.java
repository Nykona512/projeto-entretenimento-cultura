package api.dto;
import jakarta.validation.constraints.*;

public record FilmeRequest(@NotBlank String titulo, @NotNull @Min(1) Integer duracaoMinutos) {}
