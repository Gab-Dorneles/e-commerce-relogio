package br.unitins.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.itempedido.ItemPedidoDTO;
import br.unitins.ecommerce.dto.pedido.PedidoDTO;

import br.unitins.ecommerce.model.endereco.Cidade;
import br.unitins.ecommerce.model.endereco.Estado;
import br.unitins.ecommerce.model.produto.Colecao;
import br.unitins.ecommerce.model.produto.Fabricante;
import br.unitins.ecommerce.model.produto.Relogio;
import br.unitins.ecommerce.model.produto.TipoPulseira;
import br.unitins.ecommerce.model.produto.tipoRelogio.TipoRelogio;
import br.unitins.ecommerce.model.usuario.Perfil;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.repository.CidadeRepository;
import br.unitins.ecommerce.repository.ColecaoRepository;
import br.unitins.ecommerce.repository.EstadoRepository;
import br.unitins.ecommerce.repository.FabricanteRepository;
import br.unitins.ecommerce.repository.RelogioRepository;
import br.unitins.ecommerce.repository.TipoRelogioRepository;
import br.unitins.ecommerce.repository.UsuarioRepository;
import br.unitins.ecommerce.service.pedido.PedidoService;
import io.quarkus.panache.common.Sort;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PedidoServiceTest {

    @Inject
    PedidoService pedidoService;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    RelogioRepository relogioRepository;

    @Inject
    CidadeRepository cidadeRepository;

    @Inject
    EstadoRepository estadoRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    @Inject
    ColecaoRepository colecaoRepository;

    @Inject
    TipoRelogioRepository tipoRelogioRepository;

    private static Long pedidoId;
    private static String login = "cliente1";

    @BeforeEach
    @Transactional
    void setupData() {
        // Estado
        List<Estado> estados = estadoRepository.findByNome("São Paulo", Sort.by("id")).list();
        Estado estado;
        if (estados.isEmpty()) {
            estado = new Estado();
            estado.setNome("São Paulo");
            estado.setSigla("SP");
            estadoRepository.persistAndFlush(estado);
        } else {
            estado = estados.get(0);
        }

        // Cidade
        if (cidadeRepository.findByNome("Campinas", Sort.by("id")).list().isEmpty()) {
            Cidade cidade = new Cidade();
            cidade.setNome("Campinas");
            cidade.setEstado(estado);
            cidadeRepository.persistAndFlush(cidade);
        }

        // Usuário
        if (usuarioRepository.findByLogin(login) == null) {
            Usuario user = new Usuario();
            user.setNome("João Silva");
            user.setLogin(login);
            user.setEmail("teste@gmail.com");
            user.setCpf("12345678901");
            user.setSenha("1234"); 
            user.setPerfil(Perfil.USER); 

            usuarioRepository.persistAndFlush(user);
        }

        // Fabricante
        List<Fabricante> fabricantes = fabricanteRepository.findByNome("Fabricante Teste", Sort.by("id")).list();
        Fabricante fabricante;
        if (fabricantes.isEmpty()) {
            fabricante = new Fabricante();
            fabricante.setNome("Fabricante Teste");
            fabricante.setDescricao("Descrição fabricante");
            fabricante.setAnoFundacao(LocalDate.of(2000, 1, 1));
            fabricante.setLocalizacao("São Paulo");
            fabricanteRepository.persistAndFlush(fabricante);
        } else {
            fabricante = fabricantes.get(0);
        }

        // Colecao
        List<Colecao> colecoes = colecaoRepository.findByNome("Coleção Teste", Sort.by("id")).list();
        Colecao colecao;
        if (colecoes.isEmpty()) {
            colecao = new Colecao();
            colecao.setNome("Coleção Teste");
            colecao.setDescricao("Descrição coleção");
            colecaoRepository.persistAndFlush(colecao);
        } else {
            colecao = colecoes.get(0);
        }

        // TipoRelogio
        List<TipoRelogio> tiposRelogio = tipoRelogioRepository.findByNome("Tipo Teste", Sort.by("id")).list();
        TipoRelogio tipoRelogio;
        if (tiposRelogio.isEmpty()) {
            tipoRelogio = new TipoRelogio();
            tipoRelogio.setNome("Tipo Teste");
            tipoRelogio.setDescricao("Descrição Teste");
            tipoRelogioRepository.persistAndFlush(tipoRelogio);
        } else {
            tipoRelogio = tiposRelogio.get(0);
        }

        // Relogio
        if (relogioRepository.listAll().isEmpty()) {
            Relogio relogio = new Relogio();
            relogio.setNome("Relógio Teste");
            relogio.setDescricao("Descrição relógio");
            relogio.setPreco(500.0);
            relogio.setAnoLancamento(LocalDate.of(2023, 1, 1));
            relogio.setResistenciaAgua(true);
            relogio.setTemDiamante(false);
            relogio.setTipoPulseira(TipoPulseira.COURO);
            relogio.setTipoRelogio(tipoRelogio); // ou objeto, conforme sua entidade
            relogio.setColecoes(List.of(colecao.getId()));
            relogio.setFabricante(fabricante);
            relogioRepository.persistAndFlush(relogio);
        }
    }

    @Test
    @Order(1)
    void testInsertPedido() {
        Long idRelogio = relogioRepository.listAll().get(0).getId();
        List<Cidade> cidades = cidadeRepository.findByNome("Campinas", Sort.by("id")).list();
        Long idCidade = cidades.get(0).getId();

        ItemPedidoDTO item = new ItemPedidoDTO(4, 500.0, idRelogio);

        EnderecoDTO endereco = new EnderecoDTO("13000-000", "Rua A", "Centro", "123", "64564564", idCidade);

        PedidoDTO pedido = new PedidoDTO(
                endereco,
                1,
                null,
                List.of(item));

        var response = pedidoService.insert(pedido, login);

        assertNotNull(response);   
        pedidoId = response.id();
    }

    @Test
    @Order(2)
    void testFindById() {
        var response = pedidoService.findById(pedidoId);

        assertNotNull(response);
        assertEquals(pedidoId, response.id());
        assertEquals("João Silva", response.usuario().nome());
    }

  
}
