package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.ItemPedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ItemPedidoRepository implements PanacheRepository<ItemPedido> {
    
    public List<ItemPedido> findByPedidoId(Long pedidoId) {
        return list("pedido.id", pedidoId);
    }

    public List<ItemPedido> findByRelogioId(Long relogioId) {
        return list("relogio.id", relogioId);
    }
}
