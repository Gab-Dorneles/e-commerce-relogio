package br.unitins.service;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;



import br.unitins.ecommerce.dto.fabricante.FabricanteDTO;
import br.unitins.ecommerce.dto.fabricante.FabricanteResponseDTO;
import br.unitins.ecommerce.repository.FabricanteRepository;
import br.unitins.ecommerce.service.fabricante.FabricanteService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FabricanteServiceTest {
    
    @Inject
    FabricanteService fabricanteService;

    @Inject
    FabricanteRepository fabricanteRepository;

    private static Long fabricanteId;

    @Test
    @Order(1)
    void testInsert() {
        FabricanteDTO dto = new FabricanteDTO(
                "Empresa Teste",
                "Descrição da empresa",
                LocalDate.of(2000, 1, 1),
                "São Paulo"
        );

        FabricanteResponseDTO response = fabricanteService.insert(dto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Empresa Teste", response.nome());

        fabricanteId = response.id(); // guarda para os próximos testes
    }

    @Test
    @Order(2)
    void testUpdate() {
        FabricanteDTO dto = new FabricanteDTO(
                "Empresa Atualizada",
                "Descrição Atualizada",
                LocalDate.of(1999, 5, 20),
                "Rio de Janeiro"
        );

        FabricanteResponseDTO updated = fabricanteService.update(fabricanteId, dto);

        Assertions.assertEquals("Empresa Atualizada", updated.nome());
        Assertions.assertEquals("Descrição Atualizada", updated.descricao());
    }

    @Test
    @Order(3)
    void testGetByNome() {
        List<FabricanteResponseDTO> list = fabricanteService.getByNome("Atualizada", 0, 10);
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertTrue(list.get(0).nome().contains("Atualizada"));
    }

    @Test
    @Order(4)
    void testDelete() {
        fabricanteService.delete(fabricanteId);
        Assertions.assertThrows(NotFoundException.class, () -> {
            fabricanteService.update(fabricanteId, new FabricanteDTO(
                    "Empresa",
                    "Desc",
                    LocalDate.of(2001, 1, 1),
                    "SP"
            ));
        });
    }

    @Test
    void testInsertValidationError() {
        FabricanteDTO invalidDto = new FabricanteDTO(
                "", // nome inválido
                "", // descrição inválida
                null, // data nula
                "" // localização inválida
        );

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            fabricanteService.insert(invalidDto);
        });
    }
}
