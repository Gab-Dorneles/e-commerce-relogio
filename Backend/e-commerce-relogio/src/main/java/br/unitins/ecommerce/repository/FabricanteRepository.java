package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.Fabricante;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FabricanteRepository implements PanacheRepository<Fabricante> {
    
    public Fabricante findByNome(String nome) {
        return find("nome", nome).firstResult();
    }

    public boolean existsByNome(String nome) {
        return count("nome", nome) > 0;
    }
}
