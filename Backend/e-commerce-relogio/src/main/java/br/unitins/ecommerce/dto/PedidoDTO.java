package br.unitins.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {
    public Long id;
    public Long usuarioId;
    public String usuarioNome;
    public Long enderecoId;
    public String status;
    public LocalDateTime dataPedido;
    public LocalDateTime dataAtualizacao;
    public BigDecimal totalPedido;
    public List<ItemPedidoDTO> itens;
    public Long pagamentoId;
    public String observacoes;

    public PedidoDTO() {}
}
