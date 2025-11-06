package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.Nota;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class NotaRepository implements PanacheRepository<Nota> {
    
    public Nota findByNumeroNota(String numeroNota) {
        return find("numeroNota", numeroNota).firstResult();
    }

    public Nota findByPedidoId(Long pedidoId) {
        return find("pedido.id", pedidoId).firstResult();
    }

    public List<Nota> findByPagamentoId(Long pagamentoId) {
        return list("pagamento.id", pagamentoId);
    }
}
