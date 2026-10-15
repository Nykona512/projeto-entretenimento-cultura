package api.service;

import api.dto.*;
import api.mapper.*;
import api.model.*;
import api.repository.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Registra compras de até seis ingressos e consulta pedidos (R3). */
@Service
public class PedidoService {
    private final PedidoRepository pedidos;
    private final SessaoRepository sessoes;
    private final IngressoRepository ingressos;
    private final IngressoService ingressoService;
    private final IngressoMapper ingressoMapper;
    private final PedidoMapper pedidoMapper;
    public PedidoService(PedidoRepository pedidos, SessaoRepository sessoes, IngressoRepository ingressos, IngressoService ingressoService, IngressoMapper ingressoMapper, PedidoMapper pedidoMapper) {
        this.pedidos = pedidos;
        this.sessoes = sessoes;
        this.ingressos = ingressos;
        this.ingressoService = ingressoService;
        this.ingressoMapper = ingressoMapper;
        this.pedidoMapper = pedidoMapper;
    }

    @Transactional
    public PedidoResponse criar(PedidoRequest dados) {
        if (dados.ingressos().size()> 6) throw new RegraDeNegocioException("R3: O limite máximo permitido é de 6 ingressos por pedido.");
        Sessao sessao = sessoes.buscarParaVenda(dados.sessaoId()).orElseThrow(() -> new RecursoNaoEncontradoException("Sessão não encontrada: " + dados.sessaoId()));
        if (!sessao.inicio.isAfter(LocalDateTime.now())) throw new RegraDeNegocioException("A sessão já começou.");
        Pedido pedido = pedidos.save(new Pedido(LocalDateTime.now(), BigDecimal.ZERO));
        List<IngressoResponse> criados = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (ItemIngressoRequest item: dados.ingressos()) {
            Ingresso ingresso = ingressoService.criar(sessao, pedido, item.tipo());
            total = total.add(ingresso.valor);
            criados.add(ingressoMapper.paraResponse(ingresso));
        }
        pedido.valorTotal = total;
        pedidos.save(pedido);
        return pedidoMapper.paraResponse(pedido, criados);
    }

    @Transactional(readOnly = true)
    public PedidoResponse buscar(Long id) {
        Pedido pedido = pedidos.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado: " + id));
        return pedidoMapper.paraResponse(pedido, ingressos.findByPedidoId(id).stream().map(ingressoMapper::paraResponse).toList());
    }
}
