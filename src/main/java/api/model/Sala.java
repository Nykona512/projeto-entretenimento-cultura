package api.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="sala") public class Sala {

 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;

 @Column(nullable=false) public String nome;

 @Column(nullable=false) public Integer capacidade;
 
 public Sala() {} public Sala(String nome,Integer capacidade){this.nome=nome;this.capacidade=capacidade;}
}
