package br.unitins.ecommerce.service;
import br.unitins.ecommerce.util.MapperUtil;
import br.unitins.ecommerce.dto.MarcaDTO;
import br.unitins.ecommerce.form.MarcaForm;
import br.unitins.ecommerce.model.Marca;
import br.unitins.ecommerce.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MarcaService {

    @Inject
    MarcaRepository marcaRepository;

    public List<MarcaDTO> listarTodos() {
        return marcaRepository.listAll().stream()
            .map(MapperUtil::toMarcaDTO)
            .collect(Collectors.toList());
    }

    public MarcaDTO buscarPorId(Long id) {
        Marca marca = marcaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Marca não encontrada"));
        return MapperUtil.toMarcaDTO(marca);
    }

    @Transactional
    public MarcaDTO criarMarca(MarcaForm form) {
        if (marcaRepository.existsByNome(form.nome)) {
            throw new BadRequestException("Marca já existe");
        }

        Marca marca = new Marca();
        marca.nome = form.nome;
        marcaRepository.persist(marca);

        return MapperUtil.toMarcaDTO(marca);
    }

    @Transactional
    public MarcaDTO atualizarMarca(Long id, MarcaForm form) {
        Marca marca = marcaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Marca não encontrada"));

        if (!marca.nome.equals(form.nome) && marcaRepository.existsByNome(form.nome)) {
            throw new BadRequestException("Marca já existe");
        }

        marca.nome = form.nome;
        marcaRepository.persist(marca);

        return MapperUtil.toMarcaDTO(marca);
    }

    @Transactional
    public void deletarMarca(Long id) {
        Marca marca = marcaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Marca não encontrada"));
        marcaRepository.delete(marca);
    }
}
