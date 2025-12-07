package br.com.banksecure.app.service;

import br.com.banksecure.app.domain.entity.Seguro;
import br.com.banksecure.app.dto.request.SeguroRequest;
import br.com.banksecure.app.dto.request.SeguroUpdateRequest;
import br.com.banksecure.app.dto.response.SeguroResponse;
import br.com.banksecure.app.exception.SeguroExistenteException;
import br.com.banksecure.app.exception.SeguroNaoEncontradoException;
import br.com.banksecure.app.mapper.SeguroMapper;
import br.com.banksecure.app.repository.SeguroRepository;
import br.com.banksecure.app.util.ValidarAcesso;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.banksecure.app.enums.ErrorMessage.SEGURO_JA_EXISTE;
import static br.com.banksecure.app.enums.ErrorMessage.SEGURO_NAO_ENCONTRADO;


@Service
public class SeguroService {

    private final SeguroRepository repository;
    private final SeguroMapper mapper;
    private final ValidarAcesso acesso;

    public SeguroService(SeguroRepository repository, SeguroMapper mapper, ValidarAcesso acesso) {
        this.repository = repository;
        this.mapper = mapper;
        this.acesso = acesso;
    }

    @Transactional
    public SeguroResponse cadastrar(SeguroRequest request, Long funcionarioId) {
        acesso.validarAcesso(funcionarioId);

        if (repository.existsByTitulo(request.titulo())) {
            throw new SeguroExistenteException(SEGURO_JA_EXISTE.getMessage());
        }

        Seguro seguro = mapper.converterParaEntity(request);

        var seguroSalvo = repository.save(seguro);

        return mapper.converterParaResponse(seguroSalvo);
    }

    public List<SeguroResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::converterParaResponse)
                .toList();
    }

    @Transactional
    public SeguroResponse atualizar(Long id, SeguroUpdateRequest request, Long funcionarioId) {
        acesso.validarAcesso(funcionarioId);

        var seguro = repository.findById(id)
                .orElseThrow(
                        () -> new SeguroNaoEncontradoException(SEGURO_NAO_ENCONTRADO.getMessage()));

        if (request.titulo() != null && !request.titulo().isBlank()) {
            seguro.setTitulo(request.titulo());
        }

        if (request.coberturaMinima() != null && !request.coberturaMinima().isBlank()) {
            seguro.setCoberturaMinima(request.coberturaMinima());
        }

        if (request.valorPremioBase() != null) {
            seguro.setValorPremioBase(request.valorPremioBase());
        }

        Seguro seguroAtualizado = repository.save(seguro);

        return mapper.converterParaResponse(seguroAtualizado);
    }

    @Transactional
    public void excluir(Long id, Long funcionarioId) {
        acesso.validarAcesso(funcionarioId);

        Seguro seguro = repository.findById(id)
                .orElseThrow(
                        () -> new SeguroNaoEncontradoException(SEGURO_NAO_ENCONTRADO.getMessage()));

        repository.delete(seguro);
    }
}
