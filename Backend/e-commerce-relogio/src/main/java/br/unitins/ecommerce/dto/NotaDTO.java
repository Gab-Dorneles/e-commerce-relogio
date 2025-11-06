package br.unitins.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class NotaDTO {
    public Long id;
    public Long pedidoId;
    public Long pagamentoId;
    public String numeroNota;
    public LocalDateTime dataEmissao;
    public LocalDateTime dataVencimento;
    public BigDecimal totalNota;
    public BigDecimal impostos;
    public BigDecimal desconto;
    public String observacoes;

    public NotaDTO() {}
}
