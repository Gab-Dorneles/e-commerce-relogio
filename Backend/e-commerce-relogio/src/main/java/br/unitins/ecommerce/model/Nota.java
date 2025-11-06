package br.unitins.ecommerce.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "notas")
public class Nota extends PanacheEntity {

    @NotNull(message = "Pedido é obrigatório")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    public Pedido pedido;

    @NotNull(message = "Pagamento é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pagamento_id", nullable = false)
    public Pagamento pagamento;

    @Column(name = "numero_nota", nullable = false, unique = true)
    public String numeroNota;

    @Column(name = "data_emissao", nullable = false)
    public LocalDateTime dataEmissao;

    @Column(name = "data_vencimento")
    public LocalDateTime dataVencimento;

    @Column(name = "total_nota", nullable = false, precision = 10, scale = 2)
    public BigDecimal totalNota;

    @Column(name = "impostos", precision = 10, scale = 2)
    public BigDecimal impostos;

    @Column(name = "desconto", precision = 10, scale = 2)
    public BigDecimal desconto;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    public String observacoes;

    @PrePersist
    protected void onCreate() {
        dataEmissao = LocalDateTime.now();
        numeroNota = "NF-" + System.currentTimeMillis();
    }
}
