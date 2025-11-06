package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.Relogio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class RelogioRepository implements PanacheRepository<Relogio> {
    
    public List<Relogio> findByGenero(String genero) {
        return list("genero", genero);
    }

    public List<Relogio> findByPrecoBetween(BigDecimal minPreco, BigDecimal maxPreco) {
        return list("preco BETWEEN ?1 AND ?2", minPreco, maxPreco);
    }

    public List<Relogio> findByModeloId(Long modeloId) {
        return list("modeloRef.id", modeloId);
    }

    public List<Relogio> findByFabricanteId(Long fabricanteId) {
        return list("fabricante.id", fabricanteId);
    }
}
