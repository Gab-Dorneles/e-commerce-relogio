package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {
    
    public List<Endereco> findByCidade(String cidade) {
        return list("cidade", cidade);
    }

    public List<Endereco> findByEstado(String estado) {
        return list("estado", estado);
    }
}
