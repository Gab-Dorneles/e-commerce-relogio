package br.unitins.ecommerce.service;

import br.unitins.ecommerce.dto.FabricanteDTO;
import br.unitins.ecommerce.form.FabricanteForm;
import br.unitins.ecommerce.model.Fabricante;
import br.unitins.ecommerce.repository.FabricanteRepository;
import br.unitins.ecommerce.util.MapperUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FabricanteService {

    @Inject
    FabricanteRepository fabricanteRepository;

    public List<FabricanteDTO> listarTodos() {
        return fabricanteRepository.listAll().stream()
            .map(MapperUtil::toFabricanteDTO)
            .collect(Collectors.toList());
    }

    public FabricanteDTO buscarPorId(Long id) {
        Fabricante fabricante = fabricanteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Fabricante não encontrado"));
        return MapperUtil.toFabricanteDTO(fabricante);
    }

    @Transactional
    public FabricanteDTO criarFabricante(FabricanteForm form) {
        if (fabricanteRepository.existsByNome(form.nome)) {
            throw new BadRequestException("Fabricante já existe");
        }

        Fabricante fabricante = new Fabricante();
        fabricante.nome = form.nome;
        fabricanteRepository.persist(fabricante);

        return MapperUtil.toFabricanteDTO(fabricante);
    }

    @Transactional
    public FabricanteDTO atualizarFabricante(Long id, FabricanteForm form) {
        Fabricante fabricante = fabricanteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Fabricante não encontrado"));

        if (!fabricante.nome.equals(form.nome) && fabricanteRepository.existsByNome(form.nome)) {
            throw new BadRequestException("Fabricante já existe");
        }

        fabricante.nome = form.nome;
        fabricanteRepository.persist(fabricante);

        return MapperUtil.toFabricanteDTO(fabricante);
    }

    @Transactional
    public void deletarFabricante(Long id) {
        Fabricante fabricante = fabricanteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Fabricante não encontrado"));
        fabricanteRepository.delete(fabricante);
    }
}
