package br.unitins.ecommerce.dto;

public class PulseiraDTO {
    public Long id;
    public String tipo;
    public String material;
    public CorDTO cor;

    public PulseiraDTO() {}

    public PulseiraDTO(Long id, String tipo, String material) {
        this.id = id;
        this.tipo = tipo;
        this.material = material;
    }
}
