package br.unitins.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.ecommerce.dto.usuario.UsuarioDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.repository.UsuarioRepository;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioServiceTest {

    @Inject
    UsuarioService usuarioService;

    @Inject
    UsuarioRepository usuarioRepository;

    private static Long usuarioId;

    @Test
    @Order(1)
    void testInsert() {
        UsuarioDTO dto = new UsuarioDTO(
            "Lucas José",
            "070.553.381-66",
            "lucas@teste.com",
            "lucasjose",
            "senha123",
            "USER",                     
            List.of("11999999999", "1122223333")
        );

        UsuarioResponseDTO response = usuarioService.insert(dto);

        assertNotNull(response);
        assertEquals("Lucas José", response.nome());

        usuarioId = response.id();
    }

    @Test
    @Order(2)
    void testUpdate() {
        UsuarioDTO dto = new UsuarioDTO(
            "Lucas Atualizado",
            "070.553.381-66",
            "lucas@teste.com",
            "lucasjose",
            "novaSenha123",
            "USER",
            List.of("11911111111")
        );

        Usuario updated = usuarioService.update(usuarioId, dto);

        assertNotNull(updated);
        assertEquals("Lucas Atualizado", updated.getNome());
        assertEquals(1, updated.getTelefones().size());
        assertEquals("11911111111", updated.getTelefones().get(0).getNumero());
    }

    @Test
    @Order(3)
    void testGetById() {
        UsuarioResponseDTO response = usuarioService.getById(usuarioId);

        assertNotNull(response);
        assertEquals("Lucas Atualizado", response.nome());
        assertEquals("lucasjose", response.login());
    }

    @Test
    @Order(4)
    void testGetAll() {
        List<UsuarioResponseDTO> usuarios = usuarioService.getAll();
        assertFalse(usuarios.isEmpty());
    }

    @Test
    @Order(5)
    void testDelete() {
        usuarioService.delete(usuarioId);

        assertThrows(RuntimeException.class, () -> {
            usuarioService.getById(usuarioId);
        });
    }

    @Test
    void testInsertValidationError() {
        UsuarioDTO dto = new UsuarioDTO(
            "", "", "", "", "", "", List.of()
        );

        assertThrows(Exception.class, () -> {
            usuarioService.insert(dto);
        });
    }
    
}
