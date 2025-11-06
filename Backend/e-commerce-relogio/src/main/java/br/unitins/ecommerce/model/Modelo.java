package br.unitins.ecommerce.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "modelos")
public class Modelo extends PanacheEntity {

    @NotBlank(message = "Tipo do modelo é obrigatório")
    @Column(nullable = false)
    public String tipo;

    @Column(name = "ano_lancamento")
    public Integer anoLancamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", nullable = false)
    public Marca marca;
}
