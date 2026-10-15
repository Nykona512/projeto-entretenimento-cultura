package api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Entidade persistida do sistema de ingressos de cinema. */
@Entity
@Table(name = "ingresso")
public class Ingresso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sessao_id")
    public Sessao sessao;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id")
    public Pedido pedido;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TipoIngresso tipo;
    @Column(nullable = false, precision = 10, scale = 2)
    public BigDecimal valor;
    public Ingresso() {
    }

    public Ingresso(Sessao sessao, Pedido pedido, TipoIngresso tipo, BigDecimal valor) {
        this.sessao = sessao;
        this.pedido = pedido;
        this.tipo = tipo;
        this.valor = valor;
    }
}
