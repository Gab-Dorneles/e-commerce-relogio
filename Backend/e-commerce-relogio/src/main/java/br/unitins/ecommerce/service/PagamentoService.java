package br.unitins.ecommerce.service;
import br.unitins.ecommerce.email.EmailService;
import br.unitins.ecommerce.dto.PagamentoDTO;
import br.unitins.ecommerce.enums.StatusPagamento;
import br.unitins.ecommerce.enums.TipoPagamento;
import br.unitins.ecommerce.form.PagamentoForm;
import br.unitins.ecommerce.model.Pagamento;
import br.unitins.ecommerce.repository.PagamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PagamentoService {

    @Inject
    PagamentoRepository pagamentoRepository;

    @Inject
    EmailService emailService;

    public List<PagamentoDTO> listarTodos() {
        return pagamentoRepository.listAll().stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    public PagamentoDTO buscarPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));
        return converterParaDTO(pagamento);
    }

    public List<PagamentoDTO> buscarPorStatus(String status) {
        return pagamentoRepository.findByStatus(status).stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public PagamentoDTO criarPagamento(PagamentoForm form) {
        try {
            TipoPagamento.valueOf(form.tipo);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Tipo de pagamento inválido: " + form.tipo);
        }

        Pagamento pagamento = new Pagamento();
        pagamento.tipo = TipoPagamento.valueOf(form.tipo);
        pagamento.valor = form.valor;
        pagamento.numeroTransacao = form.numeroTransacao;
        pagamento.descricao = form.descricao;
        pagamentoRepository.persist(pagamento);

        return converterParaDTO(pagamento);
    }

    @Transactional
    public PagamentoDTO aprovarPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));

        if (!pagamento.status.equals(StatusPagamento.PENDENTE)) {
            throw new BadRequestException("Apenas pagamentos pendentes podem ser aprovados");
        }

        pagamento.status = StatusPagamento.APROVADO;
        pagamentoRepository.persist(pagamento);

        return converterParaDTO(pagamento);
    }

    @Transactional
    public PagamentoDTO recusarPagamento(Long id, String motivo) {
        Pagamento pagamento = pagamentoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));

        if (!pagamento.status.equals(StatusPagamento.PENDENTE) && !pagamento.status.equals(StatusPagamento.PROCESSANDO)) {
            throw new BadRequestException("Pagamento não pode ser recusado com status: " + pagamento.status);
        }

        pagamento.status = StatusPagamento.RECUSADO;
        pagamento.descricao = motivo;
        pagamentoRepository.persist(pagamento);

        return converterParaDTO(pagamento);
    }

    @Transactional
    public PagamentoDTO reembolsarPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));

        if (!pagamento.status.equals(StatusPagamento.APROVADO)) {
            throw new BadRequestException("Apenas pagamentos aprovados podem ser reembolsados");
        }

        pagamento.status = StatusPagamento.REEMBOLSADO;
        pagamentoRepository.persist(pagamento);

        return converterParaDTO(pagamento);
    }

    @Transactional
    public void deletarPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));
        pagamentoRepository.delete(pagamento);
    }

    private PagamentoDTO converterParaDTO(Pagamento pagamento) {
        PagamentoDTO dto = new PagamentoDTO();
        dto.id = pagamento.id;
        dto.tipo = pagamento.tipo.name();
        dto.valor = pagamento.valor;
        dto.status = pagamento.status.name();
        dto.dataPagamento = pagamento.dataPagamento;
        dto.dataAtualizacao = pagamento.dataAtualizacao;
        dto.numeroTransacao = pagamento.numeroTransacao;
        dto.descricao = pagamento.descricao;
        return dto;
    }
}
