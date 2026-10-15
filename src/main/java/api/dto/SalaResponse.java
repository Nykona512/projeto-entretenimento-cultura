package api.dto;

/** Dados públicos devolvidos nas consultas e cadastros de sala. */
public record SalaResponse(Long id, String nome, Integer capacidade) {
}
