package br.unitins.ecommerce.service;

import br.unitins.ecommerce.dto.UsuarioDTO;
import br.unitins.ecommerce.form.UsuarioForm;
import br.unitins.ecommerce.model.Usuario;
import br.unitins.ecommerce.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.listAll().stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return converterParaDTO(usuario);
    }

    public UsuarioDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }
        return converterParaDTO(usuario);
    }

    @Transactional
    public UsuarioDTO criarUsuario(UsuarioForm form) {
        if (usuarioRepository.existsByEmail(form.email)) {
            throw new BadRequestException("Email já está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.nome = form.nome;
        usuario.email = form.email;
        usuario.senha = form.senha; // Em produção, usar hash bcrypt
        usuarioRepository.persist(usuario);

        return converterParaDTO(usuario);
    }

    @Transactional
    public UsuarioDTO atualizarUsuario(Long id, UsuarioForm form) {
        Usuario usuario = usuarioRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (!usuario.email.equals(form.email) && usuarioRepository.existsByEmail(form.email)) {
            throw new BadRequestException("Email já está registrado");
        }

        usuario.nome = form.nome;
        usuario.email = form.email;
        if (form.senha != null && !form.senha.isBlank()) {
            usuario.senha = form.senha;
        }
        usuarioRepository.persist(usuario);

        return converterParaDTO(usuario);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        usuarioRepository.delete(usuario);
    }

    private UsuarioDTO converterParaDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.id = usuario.id;
        dto.nome = usuario.nome;
        dto.email = usuario.email;
        dto.dataCriacao = usuario.dataCriacao;
        dto.dataAtualizacao = usuario.dataAtualizacao;
        return dto;
    }
}
