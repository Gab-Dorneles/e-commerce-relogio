package br.unitins.ecommerce.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "funcoes_relogio")
public class FuncaoRelogio extends PanacheEntity {

    @NotBlank(message = "Nome da função é obrigatório")
    @Column(nullable = false, unique = true)
    public String nome;
}
