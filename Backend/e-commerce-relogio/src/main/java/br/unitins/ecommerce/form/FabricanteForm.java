package br.unitins.ecommerce.form;

import jakarta.validation.constraints.NotBlank;

public class FabricanteForm {
    
    @NotBlank(message = "Nome do fabricante é obrigatório")
    public String nome;
}
