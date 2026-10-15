package api.service;
import api.dto.*; import api.model.*; import api.repository.*;
import java.math.BigDecimal; import java.time.LocalDateTime; import java.util.ArrayList; import java.util.List;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service public class PedidoService {
 private final PedidoRepository pedidos; private final SessaoRepository sessoes; private final IngressoRepository ingressos; private final IngressoService ingressoService;
 public PedidoService(PedidoRepository pedidos,SessaoRepository sessoes,IngressoRepository ingressos,IngressoService ingressoService){this.pedidos=pedidos;this.sessoes=sessoes;this.ingressos=ingressos;this.ingressoService=ingressoService;}
 @Transactional public PedidoResponse criar(PedidoRequest dados){
  // R3: máximo de seis ingressos por compra.
  if(dados.ingressos().size()>6) throw new RegraDeNegocioException("R3: O limite máximo permitido é de 6 ingressos por pedido.");
  // Bloqueia esta sessão durante a compra para duas compras não ultrapassarem a cota.
  Sessao sessao=sessoes.buscarParaVenda(dados.sessaoId()).orElseThrow(()->new RecursoNaoEncontradoException("Sessão não encontrada: "+dados.sessaoId()));
  if(!sessao.inicio.isAfter(LocalDateTime.now())) throw new RegraDeNegocioException("A sessão já começou.");
  Pedido pedido=pedidos.save(new Pedido(LocalDateTime.now(),BigDecimal.ZERO));
  List<IngressoResponse> criados=new ArrayList<>(); BigDecimal total=BigDecimal.ZERO;
  for(ItemIngressoRequest item:dados.ingressos()){
   Ingresso ingresso=ingressoService.criar(sessao,pedido,item.tipo());
   total=total.add(ingresso.valor); criados.add(IngressoResponse.de(ingresso));
  }
  pedido.valorTotal=total; pedidos.save(pedido);
  return new PedidoResponse(pedido.id,total,criados);
 }
 @Transactional(readOnly=true) public PedidoResponse buscar(Long id){
  Pedido pedido=pedidos.findById(id).orElseThrow(()->new RecursoNaoEncontradoException("Pedido não encontrado: "+id));
  return new PedidoResponse(pedido.id,pedido.valorTotal,ingressos.findByPedidoId(id).stream().map(IngressoResponse::de).toList());
 }
}
