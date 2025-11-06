package br.unitins.ecommerce.dto;

public class FabricanteDTO {
    public Long id;
    public String nome;

    public FabricanteDTO() {}

    public FabricanteDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
