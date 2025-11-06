package br.unitins.ecommerce.dto;

import java.math.BigDecimal;

public class ItemPedidoDTO {
    public Long id;
    public Long relogioId;
    public String relogioModelo;
    public Integer quantidade;
    public BigDecimal precoUnitario;
    public BigDecimal subtotal;

    public ItemPedidoDTO() {}

    public ItemPedidoDTO(Long id, Long relogioId, String relogioModelo, Integer quantidade, BigDecimal precoUnitario, BigDecimal subtotal) {
        this.id = id;
        this.relogioId = relogioId;
        this.relogioModelo = relogioModelo;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
    }
}
