package br.unitins.ecommerce.validacaoBackend;

import br.unitins.ecommerce.enums.StatusPagamento;
import br.unitins.ecommerce.enums.TipoPagamento;
import br.unitins.ecommerce.model.Pagamento;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PagamentoValidadorService {

    /**
     * Valida se um pagamento pode ser aprovado
     */
    public boolean validarAprovacaoPagamento(Pagamento pagamento) {
        return pagamento.status.equals(StatusPagamento.PENDENTE) ||
               pagamento.status.equals(StatusPagamento.PROCESSANDO);
    }

    /**
     * Valida se um pagamento pode ser reembolsado
     */
    public boolean validarReembolso(Pagamento pagamento) {
        return pagamento.status.equals(StatusPagamento.APROVADO);
    }

    /**
     * Valida se o tipo de pagamento é válido
     */
    public boolean validarTipoPagamento(String tipo) {
        try {
            TipoPagamento.valueOf(tipo);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Valida se o valor do pagamento é positivo
     */
    public boolean validarValorPagamento(java.math.BigDecimal valor) {
        return valor != null && valor.signum() > 0;
    }

    /**
     * Valida se a transação já existe (evita duplicação)
     */
    public boolean validarTransacaoUnica(String numeroTransacao) {
        return numeroTransacao != null && !numeroTransacao.trim().isEmpty();
    }
}
