package br.unitins.ecommerce.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemPedidoForm {
    
    @NotNull(message = "ID do relógio é obrigatório")
    public Long relogioId;

    @Min(value = 1, message = "Quantidade deve ser no mínimo 1")
    public Integer quantidade;
}
