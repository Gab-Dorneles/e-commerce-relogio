package br.unitins.ecommerce.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class EnderecoForm {
    
    @NotBlank(message = "Rua é obrigatória")
    public String rua;

    @NotBlank(message = "Número é obrigatório")
    public String numero;

    public String complemento;

    @NotBlank(message = "Cidade é obrigatória")
    public String cidade;

    @NotBlank(message = "Estado é obrigatório")
    public String estado;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP deve estar no formato xxxxx-xxx ou xxxxxxxx")
    public String cep;
}
