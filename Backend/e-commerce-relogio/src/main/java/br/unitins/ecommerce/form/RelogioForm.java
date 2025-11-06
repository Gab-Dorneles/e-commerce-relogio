package br.unitins.ecommerce.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class RelogioForm {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    public String nome;

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 0, message = "O preço deve ser maior ou igual a zero")
    public BigDecimal preco;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 10, max = 500, message = "A descrição deve ter entre 10 e 500 caracteres")
    public String descricao;

    @NotNull(message = "O modelo é obrigatório")
    public Long modeloId;

    @NotNull(message = "O fabricante é obrigatório")
    public Long fabricanteId;

    @NotNull(message = "O tipo de pulseira é obrigatório")
    public Long pulseiraId;

    @NotNull(message = "A função do relógio é obrigatória")
    public Long funcaoId;

    @NotNull(message = "A cor é obrigatória")
    public Long corId;

    @NotNull(message = "O gênero é obrigatório")
    public String genero;

    public Boolean resistenteAgua;

    @Min(value = 0, message = "O estoque inicial deve ser maior ou igual a zero")
    public Integer estoqueInicial;
}