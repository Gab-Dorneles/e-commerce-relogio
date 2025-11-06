package br.unitins.ecommerce.dto;

public class MarcaDTO {
    public Long id;
    public String nome;

    public MarcaDTO() {}

    public MarcaDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
