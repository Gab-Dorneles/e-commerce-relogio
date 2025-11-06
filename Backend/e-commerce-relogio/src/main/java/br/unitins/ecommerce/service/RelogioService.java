package br.unitins.ecommerce.service;
import br.unitins.ecommerce.util.MapperUtil;
import br.unitins.ecommerce.dto.RelogioDTO;
import br.unitins.ecommerce.enums.Genero;
import br.unitins.ecommerce.form.RelogioForm;
import br.unitins.ecommerce.model.*;
import br.unitins.ecommerce.repository.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RelogioService {

    @Inject
    RelogioRepository relogioRepository;

    @Inject
    ModeloRepository modeloRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    @Inject
    FuncaoRelogioRepository funcaoRepository;

    @Inject
    PulseiraRepository pulseiraRepository;

    @Inject
    EstoqueRepository estoqueRepository;

    public List<RelogioDTO> listarTodos() {
        return relogioRepository.listAll().stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    public List<RelogioDTO> listarDigitais() {
        return relogioRepository.find("type(this) = com.seuprojeto.model.RelogioDigital").list().stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    public List<RelogioDTO> listarAnalogicos() {
        return relogioRepository.find("type(this) = com.seuprojeto.model.RelogioAnalogico").list().stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    public RelogioDTO buscarPorId(Long id) {
        Relogio relogio = relogioRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Relógio não encontrado"));
        return converterParaDTO(relogio);
    }

    @Transactional
    public RelogioDTO criarRelogio(RelogioForm form) {
        Modelo modelo = modeloRepository.findByIdOptional(form.modeloId)
            .orElseThrow(() -> new NotFoundException("Modelo não encontrado"));
        
        Fabricante fabricante = fabricanteRepository.findByIdOptional(form.fabricanteId)
            .orElseThrow(() -> new NotFoundException("Fabricante não encontrado"));

        Relogio relogio = form.tipo != null && form.tipo.equals("DIGITAL") 
            ? new RelogioDigital() 
            : new RelogioAnalogico();

        relogio.modelo = form.modelo;
        relogio.preco = form.preco;
        relogio.genero = Genero.valueOf(form.genero);
        relogio.modeloRef = modelo;
        relogio.fabricante = fabricante;
        relogio.descricao = form.descricao;

        if (form.tipo.equals("DIGITAL") && relogio instanceof RelogioDigital) {
            ((RelogioDigital) relogio).temIluminacao = form.temIluminacao != null ? form.temIluminacao : false;
        } else if (form.tipo.equals("ANALOGICO") && relogio instanceof RelogioAnalogico) {
            ((RelogioAnalogico) relogio).materialPonteiro = form.materialPonteiro;
        }

        relogioRepository.persist(relogio);

        if (form.funcaoIds != null) {
            form.funcaoIds.forEach(funcaoId -> {
                FuncaoRelogio funcao = funcaoRepository.findByIdOptional(funcaoId)
                    .orElseThrow(() -> new NotFoundException("Função não encontrada"));
                relogio.funcoes.add(funcao);
            });
        }

        if (form.pulseiraIds != null) {
            form.pulseiraIds.forEach(pulseiraId -> {
                Pulseira pulseira = pulseiraRepository.findByIdOptional(pulseiraId)
                    .orElseThrow(() -> new NotFoundException("Pulseira não encontrada"));
                relogio.pulseiras.add(pulseira);
            });
        }

        if (form.estoqueId != null) {
            Estoque estoque = estoqueRepository.findByIdOptional(form.estoqueId)
                .orElseThrow(() -> new NotFoundException("Estoque não encontrado"));
            relogio.estoque = estoque;
        }

        relogioRepository.persist(relogio);
        return converterParaDTO(relogio);
    }

    @Transactional
    public RelogioDTO atualizarRelogio(Long id, RelogioForm form) {
        Relogio relogio = relogioRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Relógio não encontrado"));

        Modelo modelo = modeloRepository.findByIdOptional(form.modeloId)
            .orElseThrow(() -> new NotFoundException("Modelo não encontrado"));
        
        Fabricante fabricante = fabricanteRepository.findByIdOptional(form.fabricanteId)
            .orElseThrow(() -> new NotFoundException("Fabricante não encontrado"));

        relogio.modelo = form.modelo;
        relogio.preco = form.preco;
        relogio.genero = Genero.valueOf(form.genero);
        relogio.modeloRef = modelo;
        relogio.fabricante = fabricante;
        relogio.descricao = form.descricao;

        relogioRepository.persist(relogio);
        return converterParaDTO(relogio);
    }

    @Transactional
    public void deletarRelogio(Long id) {
        Relogio relogio = relogioRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Relógio não encontrado"));
        relogioRepository.delete(relogio);
    }

    private RelogioDTO converterParaDTO(Relogio relogio) {
        RelogioDTO dto = new RelogioDTO();
        dto.id = relogio.id;
        dto.modelo = relogio.modelo;
        dto.preco = relogio.preco;
        dto.genero = relogio.genero.name();
        dto.modeloRef = MapperUtil.toModeloDTO(relogio.modeloRef);
        dto.fabricante = MapperUtil.toFabricanteDTO(relogio.fabricante);
        dto.descricao = relogio.descricao;
        dto.dataCriacao = relogio.dataCriacao;
        dto.tipo = relogio.getClass().getSimpleName().replace("Relogio", "").toUpperCase();
        return dto;
    }
}
