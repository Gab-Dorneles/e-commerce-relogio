package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.Pagamento;
import br.unitins.ecommerce.util.MapperUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {
    
    public List<Pagamento> findByStatus(String status) {
        return list("status", status);
    }

    public List<Pagamento> findByTipo(String tipo) {
        return list("tipo", tipo);
    }

    public List<Pagamento> findByDataPagamentoBetween(LocalDateTime inicio, LocalDateTime fim) {
        return list("dataPagamento BETWEEN ?1 AND ?2", inicio, fim);
    }

    public Pagamento findByNumeroTransacao(String numeroTransacao) {
        return find("numeroTransacao", numeroTransacao).firstResult();
    }

    public BigDecimal sumValorByStatus(String status) {
        var result = find("SELECT SUM(p.valor) FROM Pagamento p WHERE p.status = ?1", status).firstResult();
        return MapperUtil.toBigDecimal(result);
    }
}
