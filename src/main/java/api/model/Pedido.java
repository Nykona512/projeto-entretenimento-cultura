package api.model;
<<<<<<< HEAD

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
=======
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity @Table(name="pedido") public class Pedido {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(name="data_hora",nullable=false) public LocalDateTime dataHora;
 @Column(name="valor_total",nullable=false,precision=10,scale=2) public BigDecimal valorTotal;
 public Pedido() {} public Pedido(LocalDateTime dataHora,BigDecimal total){this.dataHora=dataHora;this.valorTotal=total;}
>>>>>>> e67d45c8d29cad88b0a5b4c4a3ccbbf854ec651a
}
