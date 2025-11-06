package br.unitins.ecommerce.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "enderecos")
public class Endereco extends PanacheEntity {

    @NotBlank(message = "Rua é obrigatória")
    @Column(nullable = false)
    public String rua;

    @NotBlank(message = "Cidade é obrigatória")
    @Column(nullable = false)
    public String cidade;

    @NotBlank(message = "Estado é obrigatório")
    @Column(nullable = false)
    public String estado;

    @NotBlank(message = "CEP é obrigatório")
    @Column(nullable = false)
    public String cep;

    @Column(nullable = false)
    public String numero;

    public String complemento;
}
