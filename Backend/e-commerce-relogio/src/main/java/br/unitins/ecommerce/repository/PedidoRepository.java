package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {
    
    public List<Pedido> findByUsuarioId(Long usuarioId) {
        return list("usuario.id", usuarioId);
    }

    public List<Pedido> findByStatus(String status) {
        return list("status", status);
    }

    public List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim) {
        return list("dataPedido BETWEEN ?1 AND ?2", inicio, fim);
    }

    public long countByStatus(String status) {
        return count("status", status);
    }
}
