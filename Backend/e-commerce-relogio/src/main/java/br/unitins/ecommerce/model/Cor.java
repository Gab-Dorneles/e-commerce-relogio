package br.unitins.ecommerce.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "cores")
public class Cor extends PanacheEntity {

    @NotBlank(message = "Nome da cor é obrigatório")
    @Column(nullable = false, unique = true)
    public String nome;
}
