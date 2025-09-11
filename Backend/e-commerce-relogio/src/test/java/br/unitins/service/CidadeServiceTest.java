package br.unitins.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.ecommerce.dto.cidade.CidadeDTO;
import br.unitins.ecommerce.dto.cidade.CidadeResponseDTO;
import br.unitins.ecommerce.dto.estado.EstadoDTO;
import br.unitins.ecommerce.dto.estado.EstadoResponseDTO;
import br.unitins.ecommerce.repository.CidadeRepository;
import br.unitins.ecommerce.repository.EstadoRepository;
import br.unitins.ecommerce.service.cidade.CidadeService;
import br.unitins.ecommerce.service.estado.EstadoService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CidadeServiceTest {

    @Inject
    CidadeService cidadeService;

    @Inject
    EstadoService estadoService;

    @Inject
    CidadeRepository cidadeRepository;

    @Inject
    EstadoRepository estadoRepository;

    private static Long cidadeId;
    private static Long estadoId;

    // populando a classe Estado
    @BeforeEach
    void setupEstado() {
        if (estadoId == null) {
            EstadoDTO estadoDTO = new EstadoDTO("Minas Gerais", "MG");
            EstadoResponseDTO estado = estadoService.insert(estadoDTO);
            estadoId = estado.id();
        }
    }

    @Test
    @Order(1)
    void testInsert() {
        CidadeDTO dto = new CidadeDTO("Belo Horizonte", estadoId);

        CidadeResponseDTO response = cidadeService.insert(dto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Belo Horizonte", response.nome());
        Assertions.assertEquals("MG", response.estado().sigla());

        cidadeId = response.id();
    }

    @Test
    @Order(2)
    void testUpdate() {
        CidadeDTO dto = new CidadeDTO("Uberlândia", estadoId);

        CidadeResponseDTO updated = cidadeService.update(cidadeId, dto);

        Assertions.assertEquals("Uberlândia", updated.nome());
        Assertions.assertEquals("MG", updated.estado().sigla());
    }

    @Test
    @Order(3)
    void testGetById() {
        CidadeResponseDTO response = cidadeService.getById(cidadeId);

        Assertions.assertEquals("Uberlândia", response.nome());
        Assertions.assertEquals("MG", response.estado().sigla());
    }

    @Test
    @Order(4)
    void testGetByNome() {
        List<CidadeResponseDTO> list = cidadeService.getByNome("Uber", 0, 10);

        Assertions.assertFalse(list.isEmpty());
        Assertions.assertTrue(list.get(0).nome().contains("Uber"));
    }

    @Test
    @Order(5)
    void testDelete() {
        cidadeService.delete(cidadeId);

        Assertions.assertThrows(RuntimeException.class, () -> {
            cidadeService.getById(cidadeId);
        });
    }

    @Test
    void testInsertValidationError() {
        CidadeDTO invalidDto = new CidadeDTO("", null); // nome vazio, estado nulo

        Assertions.assertThrows(Exception.class, () -> {
            cidadeService.insert(invalidDto);
        });
    }
}
