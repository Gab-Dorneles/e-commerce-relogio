package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.Pulseira;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PulseiraRepository implements PanacheRepository<Pulseira> {
    
    public List<Pulseira> findByTipo(String tipo) {
        return list("tipo", tipo);
    }

    public List<Pulseira> findByMaterial(String material) {
        return list("material", material);
    }
}
