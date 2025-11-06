package br.unitins.ecommerce.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "estoques")
public class Estoque extends PanacheEntity {

    @Min(value = 0, message = "Quantidade não pode ser negativa")
    @Column(nullable = false)
    public Integer quantidade;

    @NotBlank(message = "Localização é obrigatória")
    @Column(nullable = false)
    public String localizacao;

    @Column(name = "data_atualizacao")
    public java.time.LocalDateTime dataAtualizacao;

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = java.time.LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        dataAtualizacao = java.time.LocalDateTime.now();
    }
}
