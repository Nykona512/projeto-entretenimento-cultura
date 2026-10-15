package api.dto;

/** Dados públicos devolvidos nas consultas e cadastros de filme. */
public record FilmeResponse(Long id, String titulo, Integer duracaoMinutos) {
}
