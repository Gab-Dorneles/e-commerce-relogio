package br.unitins.ecommerce.form;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PagamentoForm {
    
    @NotBlank(message = "Tipo de pagamento é obrigatório")
    public String tipo;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Valor deve ser maior que zero")
    public BigDecimal valor;

    public String numeroTransacao;

    public String descricao;
}
