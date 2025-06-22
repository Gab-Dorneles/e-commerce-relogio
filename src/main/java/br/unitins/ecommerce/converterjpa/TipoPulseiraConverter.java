package br.unitins.ecommerce.converterjpa;

import br.unitins.ecommerce.model.produto.TipoPulseira;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoPulseiraConverter implements AttributeConverter<TipoPulseira, Integer> {
    
    @Override
    public Integer convertToDatabaseColumn(TipoPulseira tipoPulseira) {

        return tipoPulseira == null ? null : tipoPulseira.getId();
    }

    @Override
    public TipoPulseira convertToEntityAttribute(Integer label) {

        return label == null ? null : TipoPulseira.valueOf(label);
    }
}
