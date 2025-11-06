package br.unitins.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnderecoDTO {
    public Long id;
    public String rua;
    public String numero;
    public String complemento;
    public String cidade;
    public String estado;
    public String cep;

    public EnderecoDTO() {}

    public EnderecoDTO(Long id, String rua, String numero, String complemento, String cidade, String estado, String cep) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }
}
