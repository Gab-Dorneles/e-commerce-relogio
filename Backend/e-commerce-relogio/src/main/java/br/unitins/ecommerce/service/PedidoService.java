package br.unitins.ecommerce.service;

import br.unitins.ecommerce.dto.ItemPedidoDTO;
import br.unitins.ecommerce.dto.PedidoDTO;
import br.unitins.ecommerce.enums.StatusPedido;
import br.unitins.ecommerce.form.ItemPedidoForm;
import br.unitins.ecommerce.form.PedidoForm;
import br.unitins.ecommerce.model.*;
import br.unitins.ecommerce.repository.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    ItemPedidoRepository itemRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    RelogioRepository relogioRepository;

    @Inject
    EmailService emailService;

    @Inject
    EstoqueRepository estoqueRepository;

    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.listAll().stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    public List<PedidoDTO> listarPorUsuario(Long usuarioId) {
        usuarioRepository.findByIdOptional(usuarioId)
            .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return pedidoRepository.findByUsuarioId(usuarioId).stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    public PedidoDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
        return converterParaDTO(pedido);
    }

    @Transactional
    public PedidoDTO criarPedido(PedidoForm form) {
        Usuario usuario = usuarioRepository.findByIdOptional(form.usuarioId)
            .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        
        Endereco endereco = enderecoRepository.findByIdOptional(form.enderecoId)
            .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));

        if (form.itens == null || form.itens.isEmpty()) {
            throw new BadRequestException("Pedido deve conter pelo menos um item");
        }

        Pedido pedido = new Pedido();
        pedido.usuario = usuario;
        pedido.endereco = endereco;
        pedido.observacoes = form.observacoes;
        pedido.totalPedido = BigDecimal.ZERO;

        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoForm itemForm : form.itens) {
            Relogio relogio = relogioRepository.findByIdOptional(itemForm.relogioId)
                .orElseThrow(() -> new NotFoundException("Relógio não encontrado"));

            // Validar estoque
            if (relogio.estoque == null || relogio.estoque.quantidade < itemForm.quantidade) {
                throw new BadRequestException("Estoque insuficiente para o relógio: " + relogio.modelo);
            }

            ItemPedido item = new ItemPedido();
            item.pedido = pedido;
            item.relogio = relogio;
            item.quantidade = itemForm.quantidade;
            item.precoUnitario = relogio.preco;
            item.subtotal = relogio.preco.multiply(new BigDecimal(itemForm.quantidade));

            pedido.itens.add(item);
            total = total.add(item.subtotal);

            // Atualizar estoque
            relogio.estoque.quantidade -= itemForm.quantidade;
            estoqueRepository.persist(relogio.estoque);
        }

        pedido.totalPedido = total;
        pedidoRepository.persist(pedido);

        // Enviar email de confirmação
        emailService.enviarEmailConfirmacaoPedido(usuario.email, "PED-" + pedido.id, usuario.nome);

        return converterParaDTO(pedido);
    }

    @Transactional
    public PedidoDTO atualizarStatusPedido(Long id, String novoStatus) {
        Pedido pedido = pedidoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        try {
            StatusPedido status = StatusPedido.valueOf(novoStatus);
            pedido.status = status;
            pedidoRepository.persist(pedido);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Status inválido: " + novoStatus);
        }

        return converterParaDTO(pedido);
    }

    @Transactional
    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        if (!pedido.status.equals(StatusPedido.PENDENTE) && !pedido.status.equals(StatusPedido.CONFIRMADO)) {
            throw new BadRequestException("Pedido não pode ser cancelado com status: " + pedido.status);
        }

        // Restaurar estoque
        for (ItemPedido item : pedido.itens) {
            item.relogio.estoque.quantidade += item.quantidade;
            estoqueRepository.persist(item.relogio.estoque);
        }

        pedido.status = StatusPedido.CANCELADO;
        pedidoRepository.persist(pedido);
    }

    @Transactional
    public void deletarPedido(Long id) {
        Pedido pedido = pedidoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
        pedidoRepository.delete(pedido);
    }

    private PedidoDTO converterParaDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.id = pedido.id;
        dto.usuarioId = pedido.usuario.id;
        dto.usuarioNome = pedido.usuario.nome;
        dto.enderecoId = pedido.endereco.id;
        dto.status = pedido.status.name();
        dto.dataPedido = pedido.dataPedido;
        dto.dataAtualizacao = pedido.dataAtualizacao;
        dto.totalPedido = pedido.totalPedido;
        dto.observacoes = pedido.observacoes;
        if (pedido.pagamento != null) {
            dto.pagamentoId = pedido.pagamento.id;
        }
        dto.itens = pedido.itens.stream()
            .map(item -> new ItemPedidoDTO(item.id, item.relogio.id, item.relogio.modelo, 
                item.quantidade, item.precoUnitario, item.subtotal))
            .collect(Collectors.toList());
        return dto;
    }
}
