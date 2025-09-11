package br.unitins.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.ecommerce.dto.tipoRelogio.TipoRelogioDTO;
import br.unitins.ecommerce.dto.tipoRelogio.TipoRelogioResponseDTO;
import br.unitins.ecommerce.repository.TipoRelogioRepository;
import br.unitins.ecommerce.service.tipoRelogio.TipoRelogioService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TipoRelogioTest {
    

    @Inject
    TipoRelogioService tipoRelogioService;

    @Inject
    TipoRelogioRepository tipoRelogioRepository;

    private static Long tipoRelogioId;

    @Test
    @Order(1)
    void testInsert() {
        TipoRelogioDTO dto = new TipoRelogioDTO("Analógico", "Relógios com ponteiros");
        TipoRelogioResponseDTO response = tipoRelogioService.insert(dto);

        assertNotNull(response);
        assertEquals("Analógico", response.nome());

        tipoRelogioId = response.id();
    }

    @Test
    @Order(2)
    void testUpdate() {
        TipoRelogioDTO dto = new TipoRelogioDTO("Digital", "Relógios com visor eletrônico");
        TipoRelogioResponseDTO updated = tipoRelogioService.update(tipoRelogioId, dto);

        assertNotNull(updated);
        assertEquals("Digital", updated.nome());
        assertEquals("Relógios com visor eletrônico", updated.descricao());
    }

    @Test
    @Order(3)
    void testGetById() {
        TipoRelogioResponseDTO response = tipoRelogioService.getById(tipoRelogioId);

        assertNotNull(response);
        assertEquals("Digital", response.nome());
    }

    @Test
    @Order(4)
    void testGetByNome() {
        List<TipoRelogioResponseDTO> results = tipoRelogioService.getByNome("Digi", 0, 10);

        assertFalse(results.isEmpty());
        assertTrue(results.get(0).nome().contains("Digi"));
    }

    @Test
    @Order(5)
    void testDelete() {
        tipoRelogioService.delete(tipoRelogioId);

        assertThrows(RuntimeException.class, () -> {
            tipoRelogioService.getById(tipoRelogioId);
        });
    }

    @Test
    void testInsertValidationError() {
        TipoRelogioDTO dto = new TipoRelogioDTO("", ""); // Campos vazios

        assertThrows(Exception.class, () -> {
            tipoRelogioService.insert(dto);
        });
    }
}
