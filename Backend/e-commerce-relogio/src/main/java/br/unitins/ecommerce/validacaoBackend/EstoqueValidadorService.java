package br.unitins.ecommerce.validacaoBackend;

import br.unitins.ecommerce.model.Relogio;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EstoqueValidadorService {

    /**
     * Valida se um relógio possui estoque suficiente
     */
    public boolean validarEstoqueSuficiente(Relogio relogio, Integer quantidade) {
        if (relogio.estoque == null) {
            return false;
        }
        return relogio.estoque.quantidade >= quantidade;
    }

    /**
     * Valida se o estoque está abaixo do mínimo permitido (ex: 5 unidades)
     */
    public boolean validarEstoqueMinimo(Relogio relogio, Integer minimoPermitido) {
        if (relogio.estoque == null) {
            return false;
        }
        return relogio.estoque.quantidade < minimoPermitido;
    }

    /**
     * Valida se a quantidade solicitada é válida
     */
    public boolean validarQuantidadeValida(Integer quantidade) {
        return quantidade != null && quantidade > 0 && quantidade <= 9999;
    }
}
