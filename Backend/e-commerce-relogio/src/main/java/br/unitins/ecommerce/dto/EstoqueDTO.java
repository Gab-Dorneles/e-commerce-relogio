package br.unitins.ecommerce.dto;

import java.time.LocalDateTime;

public class EstoqueDTO {
    public Long id;
    public Integer quantidade;
    public String localizacao;
    public LocalDateTime dataAtualizacao;

    public EstoqueDTO() {}

    public EstoqueDTO(Long id, Integer quantidade, String localizacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.localizacao = localizacao;
    }
}
