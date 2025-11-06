package br.unitins.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ANALOGICO")
public class RelogioAnalogico extends Relogio {
    
    @Column(name = "material_ponteiro")
    public String materialPonteiro;
}
