package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.Estoque;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class EstoqueRepository implements PanacheRepository<Estoque> {
    
    public List<Estoque> findByLocalizacao(String localizacao) {
        return list("localizacao", localizacao);
    }

    public List<Estoque> findByQuantidadeLowerThan(Integer quantidade) {
        return list("quantidade < ?1", quantidade);
    }
}
