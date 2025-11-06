package br.unitins.ecommerce.enums;

public enum TipoPagamento {
    CREDITO("Cartão de Crédito"),
    DEBITO("Cartão de Débito"),
    BOLETO("Boleto Bancário"),
    PIX("Pix"),
    TRANSFERENCIA("Transferência Bancária");

    private final String descricao;

    TipoPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
