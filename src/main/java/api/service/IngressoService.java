package api.service;
import api.model.*; import api.repository.IngressoRepository;
import java.math.BigDecimal; import java.math.RoundingMode;
import org.springframework.stereotype.Service;
@Service public class IngressoService {
 private final IngressoRepository repo;
 public IngressoService(IngressoRepository repo){this.repo=repo;}
 // Chamado por PedidoService dentro da transação da compra.
 public Ingresso criar(Sessao sessao,Pedido pedido,TipoIngresso tipo){
  long vendidos=repo.countBySessaoId(sessao.id);
  if(vendidos>=sessao.sala.capacidade) throw new RegraDeNegocioException("Não há mais ingressos disponíveis para esta sessão.");
  if(tipo==TipoIngresso.MEIA){
   long cota=(long)Math.floor(sessao.sala.capacidade*0.40);
   long meias=repo.countBySessaoIdAndTipo(sessao.id,TipoIngresso.MEIA);
   if(meias>=cota) throw new RegraDeNegocioException("R1: A cota de meia-entrada desta sessão (40%) já foi atingida.");
  }
  BigDecimal valor=tipo==TipoIngresso.MEIA ? sessao.precoInteira.multiply(new BigDecimal("0.50")) : sessao.precoInteira;
  return repo.saveAndFlush(new Ingresso(sessao,pedido,tipo,valor.setScale(2,RoundingMode.HALF_UP)));
 }
}
