package br.unitins.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RelogioDTO {
    public Long id;
    public String modelo;
    public BigDecimal preco;
    public String genero;
    public ModeloDTO modeloRef;
    public FabricanteDTO fabricante;
    public List<FuncaoRelogioDTO> funcoes;
    public List<PulseiraDTO> pulseiras;
    public EstoqueDTO estoque;
    public String descricao;
    public LocalDateTime dataCriacao;
    public String tipo; // DIGITAL ou ANALOGICO

    public RelogioDTO() {}
}
