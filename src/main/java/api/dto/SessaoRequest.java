package api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SessaoRequest(@NotNull Long filmeId,@NotNull Long salaId,@NotNull LocalDateTime inicio,@NotNull @DecimalMin("0.01") BigDecimal precoInteira) {}
