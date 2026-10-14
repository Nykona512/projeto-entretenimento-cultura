package api.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="filme") public class Filme {

 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;

 @Column(nullable=false) public String titulo;

 @Column(name="duracao_minutos",nullable=false) public Integer duracaoMinutos;
 
 public Filme() {} public Filme(String titulo,Integer duracao){this.titulo=titulo;this.duracaoMinutos=duracao;}
}
