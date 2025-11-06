package br.unitins.ecommerce.service;

import br.unitins.ecommerce.dto.NotaDTO;
import br.unitins.ecommerce.form.NotaForm;
import br.unitins.ecommerce.model.Nota;
import br.unitins.ecommerce.model.Pagamento;
import br.unitins.ecommerce.model.Pedido;
import br.unitins.ecommerce.repository.NotaRepository;
import br.unitins.ecommerce.repository.PagamentoRepository;
import br.unitins.ecommerce.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class NotaService {

    @Inject
    NotaRepository notaRepository;

    @Inject
    PagamentoRepository pagamentoRepository;

    @Inject
    PedidoRepository pedidoRepository;

    public List<NotaDTO> listarTodos() {
        return notaRepository.listAll().stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    public NotaDTO buscarPorId(Long id) {
        Nota nota = notaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Nota não encontrada"));
        return converterParaDTO(nota);
    }

    public NotaDTO buscarPorNumeroNota(String numeroNota) {
        Nota nota = notaRepository.findByNumeroNota(numeroNota);
        if (nota == null) {
            throw new NotFoundException("Nota não encontrada");
        }
        return converterParaDTO(nota);
    }

    public NotaDTO buscarPorPedidoId(Long pedidoId) {
        Nota nota = notaRepository.findByPedidoId(pedidoId);
        if (nota == null) {
            throw new NotFoundException("Nota não encontrada para este pedido");
        }
        return converterParaDTO(nota);
    }

    @Transactional
    public NotaDTO criarNota(NotaForm form) {
        Pedido pedido = pedidoRepository.findByIdOptional(form.pedidoId)
            .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        Pagamento pagamento = pagamentoRepository.findByIdOptional(form.pagamentoId)
            .orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));

        // Validar se já existe nota para este pedido
        Nota notaExistente = notaRepository.findByPedidoId(form.pedidoId);
        if (notaExistente != null) {
            throw new IllegalStateException("Já existe uma nota para este pedido");
        }

        Nota nota = new Nota();
        nota.pedido = pedido;
        nota.pagamento = pagamento;
        nota.dataVencimento = form.dataVencimento;
        
        BigDecimal total = pedido.totalPedido;
        
        if (form.impostos != null) {
            nota.impostos = form.impostos;
            total = total.add(form.impostos);
        }
        
        if (form.desconto != null) {
            nota.desconto = form.desconto;
            total = total.subtract(form.desconto);
        }

        nota.totalNota = total;
        nota.observacoes = form.observacoes;
        notaRepository.persist(nota);

        return converterParaDTO(nota);
    }

    @Transactional
    public void deletarNota(Long id) {
        Nota nota = notaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Nota não encontrada"));
        notaRepository.delete(nota);
    }

    private NotaDTO converterParaDTO(Nota nota) {
        NotaDTO dto = new NotaDTO();
        dto.id = nota.id;
        dto.pedidoId = nota.pedido.id;
        dto.pagamentoId = nota.pagamento.id;
        dto.numeroNota = nota.numeroNota;
        dto.dataEmissao = nota.dataEmissao;
        dto.dataVencimento = nota.dataVencimento;
        dto.totalNota = nota.totalNota;
        dto.impostos = nota.impostos;
        dto.desconto = nota.desconto;
        dto.observacoes = nota.observacoes;
        return dto;
    }
}
