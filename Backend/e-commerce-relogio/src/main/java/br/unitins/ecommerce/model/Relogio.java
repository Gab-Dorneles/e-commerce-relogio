package br.unitins.ecommerce.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "relogios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_relogio")
public class Relogio extends PanacheEntity {

    @NotBlank(message = "Modelo é obrigatório")
    @Column(nullable = false)
    public String modelo;

    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    public BigDecimal preco;

    @NotNull(message = "Gênero é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public br.unitins.ecommerce.enums.Genero genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modelo_id", nullable = false)
    public Modelo modeloRef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabricante_id", nullable = false)
    public Fabricante fabricante;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "relogio_funcoes",
        joinColumns = @JoinColumn(name = "relogio_id"),
        inverseJoinColumns = @JoinColumn(name = "funcao_id")
    )
    public List<FuncaoRelogio> funcoes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "relogio_pulseiras",
        joinColumns = @JoinColumn(name = "relogio_id"),
        inverseJoinColumns = @JoinColumn(name = "pulseira_id")
    )
    public List<Pulseira> pulseiras = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "estoque_id")
    public Estoque estoque;

    @Column(name = "descricao", columnDefinition = "TEXT")
    public String descricao;

    @Column(name = "data_criacao")
    public java.time.LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = java.time.LocalDateTime.now();
    }
}
