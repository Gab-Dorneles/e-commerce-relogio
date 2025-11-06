package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.Cor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CorRepository implements PanacheRepository<Cor> {
    
    public Cor findByNome(String nome) {
        return find("nome", nome).firstResult();
    }
}
