package br.unitins.ecommerce.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
public class Pagamento extends PanacheEntity {

    @NotNull(message = "Tipo de pagamento é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public br.unitins.ecommerce.enums.TipoPagamento tipo;

    @DecimalMin(value = "0.0", inclusive = false, message = "Valor deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    public BigDecimal valor;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public br.unitins.ecommerce.enums.StatusPagamento status;

    @Column(name = "data_pagamento")
    public LocalDateTime dataPagamento;

    @Column(name = "data_atualizacao")
    public LocalDateTime dataAtualizacao;

    @Column(name = "numero_transacao", unique = true)
    public String numeroTransacao;

    @Column(name = "descricao", columnDefinition = "TEXT")
    public String descricao;

    @PrePersist
    protected void onCreate() {
        dataPagamento = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        status = br.unitins.ecommerce.enums.StatusPagamento.PENDENTE;
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}
