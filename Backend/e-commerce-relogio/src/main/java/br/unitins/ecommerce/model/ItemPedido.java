package br.unitins.ecommerce.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_pedido")
public class ItemPedido extends PanacheEntity {

    @NotNull(message = "Pedido é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    public Pedido pedido;

    @NotNull(message = "Relógio é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relogio_id", nullable = false)
    public Relogio relogio;

    @Min(value = 1, message = "Quantidade deve ser no mínimo 1")
    @Column(nullable = false)
    public Integer quantidade;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    public BigDecimal precoUnitario;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    public BigDecimal subtotal;

    @PrePersist
    protected void calcularSubtotal() {
        if (precoUnitario != null && quantidade != null) {
            subtotal = precoUnitario.multiply(new BigDecimal(quantidade));
        }
    }
}
