package br.unitins.ecommerce.validacaoBackend;

import br.unitins.ecommerce.model.Pedido;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoValidadorService {

    /**
     * Valida se um pedido pode ser cancelado baseado em seu status
     */
    public boolean validarCancelamentoPedido(Pedido pedido) {
        return pedido.status.name().equals("PENDENTE") || 
               pedido.status.name().equals("CONFIRMADO");
    }

    /**
     * Valida se o pedido possui itens
     */
    public boolean validarPedidoPossuiItens(Pedido pedido) {
        return pedido.itens != null && !pedido.itens.isEmpty();
    }

    /**
     * Valida se todos os itens do pedido têm preços válidos
     */
    public boolean validarPrecosItens(Pedido pedido) {
        return pedido.itens.stream()
            .allMatch(item -> item.precoUnitario != null && item.precoUnitario.signum() > 0);
    }

    /**
     * Calcula o total do pedido validando os itens
     */
    public java.math.BigDecimal calcularTotalPedido(Pedido pedido) {
        return pedido.itens.stream()
            .map(item -> item.subtotal)
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }
}
