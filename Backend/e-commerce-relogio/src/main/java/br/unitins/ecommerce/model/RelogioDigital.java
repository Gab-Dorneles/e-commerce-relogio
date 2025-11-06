package br.unitins.ecommerce.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DIGITAL")
public class RelogioDigital extends Relogio {
    public Boolean temIluminacao = false;
}
