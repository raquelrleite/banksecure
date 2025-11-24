package br.com.banksecure.app.service;

import br.com.banksecure.app.domain.entity.Seguro;
import br.com.banksecure.app.dto.request.SeguroRequest;
import br.com.banksecure.app.dto.request.SeguroUpdateRequest;
import br.com.banksecure.app.dto.response.SeguroResponse;
import br.com.banksecure.app.exception.SeguroNaoEncontradoException;
import br.com.banksecure.app.mapper.SeguroMapper;
import br.com.banksecure.app.repository.SeguroRepository;
import org.springframework.stereotype.Service;

import static br.com.banksecure.app.enums.ErrorMessage.SEGURO_NAO_ENCONTRADO;

@Service
public class SeguroService {

    private final SeguroRepository seguroRepository;
    private final SeguroMapper mapper;


    public SeguroService(SeguroRepository seguroRepository, SeguroMapper mapper) {
        this.seguroRepository = seguroRepository;
        this.mapper = mapper;
    }

    public SeguroResponse cadastrar(SeguroRequest request) {
        Seguro seguro = mapper.converterParaEntity(request);

        seguroRepository.save(seguro);

        return mapper.converterParaResponse(seguro);
    }

    public SeguroResponse atualizar(Long id, SeguroUpdateRequest request) {
        Seguro seguro = seguroRepository.findById(id)
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

        Seguro seguroAtualizado = seguroRepository.save(seguro);

        return mapper.converterParaResponse(seguroAtualizado);
    }

    public void excluir(Long id) {
        Seguro seguro = seguroRepository.findById(id)
                .orElseThrow(
                        () -> new SeguroNaoEncontradoException(SEGURO_NAO_ENCONTRADO.getMessage()));

        seguroRepository.delete(seguro);
    }
}
