package br.com.banksecure.app.service;


import br.com.banksecure.app.domain.entity.Apolice;
import br.com.banksecure.app.domain.entity.Cliente;
import br.com.banksecure.app.domain.entity.Seguro;
import br.com.banksecure.app.dto.request.ApoliceRequest;
import br.com.banksecure.app.dto.response.ApoliceResponse;
import br.com.banksecure.app.exception.ClienteNaoEncontradoException;
import br.com.banksecure.app.exception.SeguroNaoEncontradoException;
import br.com.banksecure.app.mapper.ApoliceMapper;
import br.com.banksecure.app.repository.ApoliceRepository;
import br.com.banksecure.app.repository.ClienteRepository;
import br.com.banksecure.app.repository.SeguroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static br.com.banksecure.app.enums.ErrorMessage.CLIENTE_NAO_ENCONTRADO;
import static br.com.banksecure.app.enums.ErrorMessage.SEGURO_NAO_ENCONTRADO;

@Service
public class ApoliceService {

    private final ApoliceRepository apoliceRepository;
    private final ApoliceMapper mapper;
    private final ClienteRepository clienteRepository;
    private final SeguroRepository seguroRepository;

    public ApoliceService(ApoliceRepository apoliceRepository, ApoliceMapper mapper, ClienteRepository clienteRepository, SeguroRepository seguroRepository) {
        this.apoliceRepository = apoliceRepository;
        this.mapper = mapper;
        this.clienteRepository = clienteRepository;
        this.seguroRepository = seguroRepository;
    }

    @Transactional
    public BigDecimal calcularValorFinal(ApoliceRequest request){

        Seguro seguro = seguroRepository.findById(request.idSeguro())
                .orElseThrow(
                        () -> new SeguroNaoEncontradoException(SEGURO_NAO_ENCONTRADO.getMessage()));


        Cliente cliente = clienteRepository.findById(request.idCliente())
                .orElseThrow(
                        () -> new ClienteNaoEncontradoException(CLIENTE_NAO_ENCONTRADO.getMessage()));

        BigDecimal valorBase = seguro.getValorPremioBase();

        //aplicar aqui a logica rf07, linha abaixo escrita somente para o codigo não dar erro
        BigDecimal valorFinal = valorBase;


    return valorFinal;
    }

    public ApoliceResponse gerarApolice (ApoliceRequest request){
        Seguro seguro = seguroRepository.findById(request.idSeguro())
                .orElseThrow(
                        () -> new SeguroNaoEncontradoException(SEGURO_NAO_ENCONTRADO.getMessage()));


        Cliente cliente = clienteRepository.findById(request.idCliente())
                .orElseThrow(
                        () -> new ClienteNaoEncontradoException(CLIENTE_NAO_ENCONTRADO.getMessage()));

        BigDecimal valorFinal = calcularValorFinal(request);

        Apolice apolice = new Apolice();
        apolice.setCliente(cliente);
        apolice.setSeguro(seguro);
        apolice.setValorFinal(valorFinal);

        apolice.setInicioVigencia(LocalDate.now());
        apolice.setFimVigencia(LocalDate.now().plusYears(1));

        Apolice apoliceSalva = apoliceRepository.save(apolice);

        return mapper.converterParaResponse(apoliceSalva);

    }
}
