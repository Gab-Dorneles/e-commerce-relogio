package br.unitins.ecommerce.enums;

public enum Genero {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    UNISSEX("Unissex");

    private final String descricao;

    Genero(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
