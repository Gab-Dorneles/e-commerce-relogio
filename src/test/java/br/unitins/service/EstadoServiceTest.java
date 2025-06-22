package br.unitins.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.ecommerce.dto.estado.EstadoDTO;
import br.unitins.ecommerce.dto.estado.EstadoResponseDTO;
import br.unitins.ecommerce.model.endereco.Estado;
import br.unitins.ecommerce.repository.EstadoRepository;
import br.unitins.ecommerce.service.estado.EstadoService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EstadoServiceTest {
    
    @Inject
    EstadoService estadoService;

    @Inject
    EstadoRepository estadoRepository;

    private static Long estadoId;

    @Test
    @Order(1)
    void testInsert() {
        EstadoDTO dto = new EstadoDTO("Paraná", "PR");

        EstadoResponseDTO response = estadoService.insert(dto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Paraná", response.nome());

        estadoId = response.id();
    }

    @Test
    @Order(2)
    void testUpdate() {
        EstadoDTO dto = new EstadoDTO("Santa Catarina", "SC");

        Estado updated = estadoService.update(estadoId, dto);

        Assertions.assertEquals("Santa Catarina", updated.getNome());
        Assertions.assertEquals("SC", updated.getSigla());
    }

    @Test
    @Order(3)
    void testFindByNome() {
        List<EstadoResponseDTO> list = estadoService.getByNome("Santa", 0, 10);
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertTrue(list.get(0).nome().contains("Santa"));
    }

    @Test
    @Order(4)
    void testDelete() {
        estadoService.delete(estadoId);
        Assertions.assertThrows(RuntimeException.class, () -> {
            estadoService.update(estadoId, new EstadoDTO("RS", "RS"));
        });
    }

    @Test
    void testInsertWithInvalidSigla() {
        EstadoDTO invalid = new EstadoDTO("Rio Grande do Norte", "RNxxx");

        Assertions.assertThrows(Exception.class, () -> {
            estadoService.insert(invalid);
        });
    }
}
