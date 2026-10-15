package api.service;

import api.dto.IngressoResponse;
import api.mapper.IngressoMapper;
import api.model.*;
import api.repository.IngressoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Emite ingressos no pedido, respeitando lotação e cota de meia (R1). */
@Service
public class IngressoService {
    private final IngressoRepository repo;
    private final IngressoMapper mapper;
    public IngressoService(IngressoRepository repo, IngressoMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public Ingresso criar(Sessao sessao, Pedido pedido, TipoIngresso tipo) {
        long vendidos = repo.countBySessaoId(sessao.id);
        if (vendidos>= sessao.sala.capacidade) throw new RegraDeNegocioException("Não há mais ingressos disponíveis para esta sessão.");
        if (tipo == TipoIngresso.MEIA) {
            long cota = (long) Math.floor(sessao.sala.capacidade * 0.40);
            long meias = repo.countBySessaoIdAndTipo(sessao.id, TipoIngresso.MEIA);
            if (meias>= cota) throw new RegraDeNegocioException("R1: A cota de meia-entrada desta sessão (40%) já foi atingida.");
        }
        BigDecimal valor = tipo == TipoIngresso.MEIA ? sessao.precoInteira.multiply(new BigDecimal("0.50")): sessao.precoInteira;
        return repo.saveAndFlush(new Ingresso(sessao, pedido, tipo, valor.setScale(2, RoundingMode.HALF_UP)));
    }

    @Transactional(readOnly = true)
    public List<IngressoResponse> listar() {
        return repo.findAll().stream().map(mapper::paraResponse).toList();
    }

    @Transactional(readOnly = true)
    public IngressoResponse buscar(Long id) {
        return mapper.paraResponse(repo.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Ingresso não encontrado: " + id)));
    }
}
