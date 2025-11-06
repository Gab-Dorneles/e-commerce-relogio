package br.unitins.ecommerce.form;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class PedidoForm {
    
    @NotNull(message = "ID do usuário é obrigatório")
    public Long usuarioId;

    @NotNull(message = "ID do endereço é obrigatório")
    public Long enderecoId;

    @NotNull(message = "Itens do pedido são obrigatórios")
    public List<ItemPedidoForm> itens;

    public String observacoes;
}
