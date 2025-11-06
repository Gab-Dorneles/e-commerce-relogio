package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.FuncaoRelogio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FuncaoRelogioRepository implements PanacheRepository<FuncaoRelogio> {
    
    public FuncaoRelogio findByNome(String nome) {
        return find("nome", nome).firstResult();
    }
}
