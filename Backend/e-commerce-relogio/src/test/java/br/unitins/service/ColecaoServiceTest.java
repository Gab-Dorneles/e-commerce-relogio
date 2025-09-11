package br.unitins.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.ecommerce.dto.colecao.ColecaoDTO;
import br.unitins.ecommerce.dto.colecao.ColecaoResponseDTO;
import br.unitins.ecommerce.repository.ColecaoRepository;
import br.unitins.ecommerce.service.colecao.ColecaoService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ColecaoServiceTest {

    
    @Inject
    ColecaoService colecaoService;

    @Inject
    ColecaoRepository colecaoRepository;

    private static Long colecaoId;

    @Test
    @Order(1)
    void testInsert() {
        ColecaoDTO dto = new ColecaoDTO("Verão 2025", "Coleção inspirada em tons solares");

        ColecaoResponseDTO response = colecaoService.insert(dto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Verão 2025", response.nome());

        colecaoId = response.id();
    }

    @Test
    @Order(2)
    void testUpdate() {
        ColecaoDTO dto = new ColecaoDTO("Outono 2025", "Nova descrição");

        ColecaoResponseDTO updated = colecaoService.update(colecaoId, dto);

        Assertions.assertEquals("Outono 2025", updated.nome());
        Assertions.assertEquals("Nova descrição", updated.descricao());
    }

    @Test
    @Order(3)
    void testFindByNome() {
        List<ColecaoResponseDTO> list = colecaoService.getByNome("Outono", 0, 10);
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertTrue(list.get(0).nome().contains("Outono"));
    }

    @Test
    void testInsertValidationError() {
        ColecaoDTO invalidDto = new ColecaoDTO("", ""); // nome e descrição vazios

        Assertions.assertThrows(Exception.class, () -> {
            colecaoService.insert(invalidDto);
        });
    }
    
}
