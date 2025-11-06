package br.unitins.ecommerce.form;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class NotaForm {
    
    @NotNull(message = "ID do pedido é obrigatório")
    public Long pedidoId;

    @NotNull(message = "ID do pagamento é obrigatório")
    public Long pagamentoId;

    public BigDecimal impostos;

    public BigDecimal desconto;

    public LocalDateTime dataVencimento;

    public String observacoes;
}
