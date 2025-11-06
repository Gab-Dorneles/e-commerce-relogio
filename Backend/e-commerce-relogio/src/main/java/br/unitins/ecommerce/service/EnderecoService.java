package br.unitins.ecommerce.service;

import br.unitins.ecommerce.dto.EnderecoDTO;
import br.unitins.ecommerce.form.EnderecoForm;
import br.unitins.ecommerce.model.Endereco;
import br.unitins.ecommerce.repository.EnderecoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EnderecoService {

    @Inject
    EnderecoRepository enderecoRepository;

    public List<EnderecoDTO> listarTodos() {
        return enderecoRepository.listAll().stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    public EnderecoDTO buscarPorId(Long id) {
        Endereco endereco = enderecoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
        return converterParaDTO(endereco);
    }

    @Transactional
    public EnderecoDTO criarEndereco(EnderecoForm form) {
        Endereco endereco = new Endereco();
        endereco.rua = form.rua;
        endereco.numero = form.numero;
        endereco.complemento = form.complemento;
        endereco.cidade = form.cidade;
        endereco.estado = form.estado;
        endereco.cep = form.cep;
        enderecoRepository.persist(endereco);

        return converterParaDTO(endereco);
    }

    @Transactional
    public EnderecoDTO atualizarEndereco(Long id, EnderecoForm form) {
        Endereco endereco = enderecoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));

        endereco.rua = form.rua;
        endereco.numero = form.numero;
        endereco.complemento = form.complemento;
        endereco.cidade = form.cidade;
        endereco.estado = form.estado;
        endereco.cep = form.cep;
        enderecoRepository.persist(endereco);

        return converterParaDTO(endereco);
    }

    @Transactional
    public void deletarEndereco(Long id) {
        Endereco endereco = enderecoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
        enderecoRepository.delete(endereco);
    }

    private EnderecoDTO converterParaDTO(Endereco endereco) {
        return new EnderecoDTO(endereco.id, endereco.rua, endereco.numero, 
            endereco.complemento, endereco.cidade, endereco.estado, endereco.cep);
    }
}
