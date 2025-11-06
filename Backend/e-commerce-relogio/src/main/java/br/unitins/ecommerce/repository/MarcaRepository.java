package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.Marca;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MarcaRepository implements PanacheRepository<Marca> {
    
    public Marca findByNome(String nome) {
        return find("nome", nome).firstResult();
    }

    public boolean existsByNome(String nome) {
        return count("nome", nome) > 0;
    }
}
