package api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Entidade persistida do sistema de ingressos de cinema. */
@Entity
@Table(name = "sessao")
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "filme_id")
    public Filme filme;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sala_id")
    public Sala sala;
    @Column(nullable = false)
    public LocalDateTime inicio;
    @Column(name = "preco_inteira", nullable = false, precision = 10, scale = 2)
    public BigDecimal precoInteira;
    public Sessao() {
    }

    public Sessao(Filme filme, Sala sala, LocalDateTime inicio, BigDecimal preco) {
        this.filme = filme;
        this.sala = sala;
        this.inicio = inicio;
        this.precoInteira = preco;
    }
}
