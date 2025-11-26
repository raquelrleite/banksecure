package br.com.banksecure.app.service;

import br.com.banksecure.app.domain.entity.Seguro;
import br.com.banksecure.app.dto.request.SeguroRequest;
import br.com.banksecure.app.dto.request.SeguroUpdateRequest;
import br.com.banksecure.app.dto.response.SeguroResponse;
import br.com.banksecure.app.exception.AcessoNegadoException;
import br.com.banksecure.app.exception.SeguroNaoEncontradoException;
import br.com.banksecure.app.mapper.SeguroMapper;
import br.com.banksecure.app.repository.FuncionarioRepository;
import br.com.banksecure.app.repository.SeguroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.banksecure.app.enums.ErrorMessage.ACESSO_NEGADO;
import static br.com.banksecure.app.enums.ErrorMessage.SEGURO_NAO_ENCONTRADO;

@Service
public class SeguroService {

    private final SeguroRepository repository;
    private final FuncionarioRepository funcRepository;
    private final SeguroMapper mapper;


    public SeguroService(SeguroRepository repository, FuncionarioRepository funcRepository, SeguroMapper mapper) {
        this.repository = repository;
        this.funcRepository = funcRepository;
        this.mapper = mapper;
    }

    public SeguroResponse cadastrar(SeguroRequest request, Long funcionarioId) {
        validarAcesso(funcionarioId);

        Seguro seguro = mapper.converterParaEntity(request);

        repository.save(seguro);

        return mapper.converterParaResponse(seguro);
    }


    public List<SeguroResponse> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::converterParaResponse)
                .toList();
    }

    public SeguroResponse atualizar(Long id, SeguroUpdateRequest request, Long funcionarioId) {
        validarAcesso(funcionarioId);

        Seguro seguro = repository.findById(id)
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

    public void excluir(Long id, Long funcionarioId) {
        validarAcesso(funcionarioId);

        Seguro seguro = repository.findById(id)
                .orElseThrow(
                        () -> new SeguroNaoEncontradoException(SEGURO_NAO_ENCONTRADO.getMessage()));

        repository.delete(seguro);
    }

    private void validarAcesso(Long funcionarioId) {
        if (funcionarioId == null || !funcRepository.existsById(funcionarioId)) {
            throw new AcessoNegadoException(ACESSO_NEGADO.getMessage());
        }
    }
}
