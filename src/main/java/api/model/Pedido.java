package api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Entidade persistida do sistema de ingressos de cinema. */
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "data_hora", nullable = false)
    public LocalDateTime dataHora;
    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    public BigDecimal valorTotal;
    public Pedido() {
    }

    public Pedido(LocalDateTime dataHora, BigDecimal total) {
        this.dataHora = dataHora;
        this.valorTotal = total;
    }
}
