package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.Modelo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ModeloRepository implements PanacheRepository<Modelo> {
    
    public List<Modelo> findByMarcaId(Long marcaId) {
        return list("marca.id", marcaId);
    }

    public List<Modelo> findByTipo(String tipo) {
        return list("tipo", tipo);
    }
}
