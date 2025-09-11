package br.unitins.ecommerce.dto.pedido;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.unitins.ecommerce.dto.endereco.EnderecoResponseDTO;
import br.unitins.ecommerce.dto.itempedido.ItemPedidoResponseDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.model.pedido.Pedido;
import br.unitins.ecommerce.model.pedido.pagamento.BoletoBancario;
import br.unitins.ecommerce.model.pedido.pagamento.CartaoCredito;
import br.unitins.ecommerce.model.pedido.pagamento.Pagamento;
import br.unitins.ecommerce.model.pedido.pagamento.Pix;

public record PedidoResponseDTO(
    Long id,
    LocalDateTime dataHoraPedido,
    String totalPedido,
    String formaPagamento,
    String numeroCartaoUsado,
    String statusPagamento,
    LocalDate dataPagamento,
    EnderecoResponseDTO enderecoResponseDTO,
    UsuarioResponseDTO usuario,
    List<ItemPedidoResponseDTO> itens
) {

    public PedidoResponseDTO(Pedido pedido) {
        this(
            pedido.getId(),
            pedido.getDataHoraPedido(),
            formatarTotal(pedido.getTotalPedido()),
            definirFormaPagamento(pedido.getPagamento()),
            extrairNumeroCartao(pedido.getPagamento()),
            definirStatusPagamento(pedido.getPagamento()),
            pedido.getPagamento() != null ? pedido.getPagamento().getDataConfirmacaoPagamento() : null,
            pedido.getEndereco() != null ? new EnderecoResponseDTO(pedido.getEndereco()) : null,
            pedido.getUsuario() != null && pedido.getUsuario().getPerfil() != null
                ? new UsuarioResponseDTO(pedido.getUsuario(), pedido.getUsuario().getPerfil().getLabel())
                : null,
            pedido.getItens() != null
                ? ItemPedidoResponseDTO.valueOf(pedido.getItens())
                : List.of()
        );
    }

    private static String formatarTotal(Double total) {
        return total != null ? "R$ " + String.format("%.2f", total) : "R$ 0.00";
    }

    private static String definirFormaPagamento(Pagamento pagamento) {
        if (pagamento instanceof Pix) return "Pix";
        if (pagamento instanceof BoletoBancario) return "Boleto Bancário";
        if (pagamento instanceof CartaoCredito) return "Cartão de Crédito";
        return "Desconhecido";
    }

    private static String definirStatusPagamento(Pagamento pagamento) {
        if (pagamento == null) return null;
        return Boolean.TRUE.equals(pagamento.getConfirmacaoPagamento())
            ? "Pagamento efetuado"
            : "Pagamento não efetuado";
    }

    private static String extrairNumeroCartao(Pagamento pagamento) {
        if (pagamento instanceof CartaoCredito cartaoCredito) {
            return cartaoCredito.getNumeroDoCartao();
        }
        return null;
    }
}
