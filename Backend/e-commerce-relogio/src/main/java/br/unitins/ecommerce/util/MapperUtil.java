package br.unitins.ecommerce.util;

import br.unitins.ecommerce.dto.*;
import br.unitins.ecommerce.model.*;
import java.math.BigDecimal;

public class MapperUtil {
    
    public static BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof Number) {
            return new BigDecimal(value.toString());
        }
        try {
            return new BigDecimal(value.toString());
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
    
    public static UsuarioDTO toUsuarioDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.id = usuario.id;
        dto.nome = usuario.nome;
        dto.email = usuario.email;
        dto.dataCriacao = usuario.dataCriacao;
        dto.dataAtualizacao = usuario.dataAtualizacao;
        return dto;
    }

    public static EnderecoDTO toEnderecoDTO(Endereco endereco) {
        return new EnderecoDTO(endereco.id, endereco.rua, endereco.numero,
            endereco.complemento, endereco.cidade, endereco.estado, endereco.cep);
    }

    public static MarcaDTO toMarcaDTO(Marca marca) {
        MarcaDTO dto = new MarcaDTO();
        dto.id = marca.id;
        dto.nome = marca.nome;
        return dto;
    }

    public static FabricanteDTO toFabricanteDTO(Fabricante fabricante) {
        FabricanteDTO dto = new FabricanteDTO();
        dto.id = fabricante.id;
        dto.nome = fabricante.nome;
        return dto;
    }

    public static ModeloDTO toModeloDTO(Modelo modelo) {
        ModeloDTO dto = new ModeloDTO();
        dto.id = modelo.id;
        dto.tipo = modelo.tipo;
        dto.anoLancamento = modelo.anoLancamento;
        if (modelo.marca != null) {
            dto.marca = toMarcaDTO(modelo.marca);
        }
        return dto;
    }

    public static PagamentoDTO toPagamentoDTO(Pagamento pagamento) {
        PagamentoDTO dto = new PagamentoDTO();
        dto.id = pagamento.id;
        dto.tipo = pagamento.tipo.name();
        dto.valor = pagamento.valor;
        dto.status = pagamento.status.name();
        dto.dataPagamento = pagamento.dataPagamento;
        dto.numeroTransacao = pagamento.numeroTransacao;
        return dto;
    }

    public static PedidoDTO toPedidoDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.id = pedido.id;
        dto.usuarioId = pedido.usuario.id;
        dto.usuarioNome = pedido.usuario.nome;
        dto.status = pedido.status.name();
        dto.totalPedido = pedido.totalPedido;
        return dto;
    }
}
