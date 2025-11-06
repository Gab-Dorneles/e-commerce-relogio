package br.unitins.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTO {
    public Long id;
    public String nome;
    public String email;
    public LocalDateTime dataCriacao;
    public LocalDateTime dataAtualizacao;

    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }
}
