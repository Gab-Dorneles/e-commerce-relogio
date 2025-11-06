package br.unitins.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagamentoDTO {
    public Long id;
    public String tipo;
    public BigDecimal valor;
    public String status;
    public LocalDateTime dataPagamento;
    public LocalDateTime dataAtualizacao;
    public String numeroTransacao;
    public String descricao;

    public PagamentoDTO() {}

    public PagamentoDTO(Long id, String tipo, BigDecimal valor, String status) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.status = status;
    }
}
