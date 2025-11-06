package br.unitins.ecommerce.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "pulseiras")
public class Pulseira extends PanacheEntity {

    @NotBlank(message = "Tipo de pulseira é obrigatório")
    @Column(nullable = false)
    public String tipo;

    @NotBlank(message = "Material da pulseira é obrigatório")
    @Column(nullable = false)
    public String material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cor_id", nullable = false)
    public Cor cor;
}
