package br.unitins.ecommerce.form;

import jakarta.validation.constraints.NotBlank;

public class MarcaForm {
    
    @NotBlank(message = "Nome da marca é obrigatório")
    public String nome;
}
