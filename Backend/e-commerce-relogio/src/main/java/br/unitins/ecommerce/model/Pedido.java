package br.unitins.ecommerce.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido extends PanacheEntity {

    @NotNull(message = "Usuário é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    public Usuario usuario;

    @NotNull(message = "Endereço de entrega é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", nullable = false)
    public Endereco endereco;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public br.unitins.ecommerce.enums.StatusPedido status;

    @Column(name = "data_pedido", nullable = false)
    public LocalDateTime dataPedido;

    @Column(name = "data_atualizacao")
    public LocalDateTime dataAtualizacao;

    @Column(name = "total_pedido", nullable = false, precision = 10, scale = 2)
    public BigDecimal totalPedido;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<ItemPedido> itens = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pagamento_id")
    public Pagamento pagamento;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    public String observacoes;

    @PrePersist
    protected void onCreate() {
        dataPedido = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        status = br.unitins.ecommerce.enums.StatusPedido.PENDENTE;
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}
