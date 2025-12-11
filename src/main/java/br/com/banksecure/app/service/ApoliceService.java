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
import br.com.banksecure.app.util.ValidarAcesso;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

import static br.com.banksecure.app.enums.ErrorMessage.CLIENTE_NAO_ENCONTRADO;
import static br.com.banksecure.app.enums.ErrorMessage.SEGURO_NAO_ENCONTRADO;

@Service
public class ApoliceService {

    private final ApoliceRepository apoliceRepository;
    private final ApoliceMapper mapper;
    private final ClienteRepository clienteRepository;
    private final SeguroRepository seguroRepository;
    private final ValidarAcesso acesso;


    public ApoliceService(ApoliceRepository apoliceRepository, ApoliceMapper mapper, ClienteRepository clienteRepository, SeguroRepository seguroRepository, ValidarAcesso acesso) {
        this.apoliceRepository = apoliceRepository;
        this.mapper = mapper;
        this.clienteRepository = clienteRepository;
        this.seguroRepository = seguroRepository;
        this.acesso = acesso;
    }

    @Transactional
    private BigDecimal calcularValorFinal(BigDecimal valorBase, LocalDate dataNascimento){

        BigDecimal taxaFixa = valorBase.multiply(new BigDecimal("0.05"));
        BigDecimal taxaDeRisco = new BigDecimal("1.10");
        BigDecimal taxaPorIdade = new BigDecimal("100");

        BigDecimal valorFinal = valorBase.add(taxaFixa);

        int idade = Period.between(dataNascimento,LocalDate.now()).getYears();
        if(idade>60){
            valorFinal = valorFinal.add(taxaPorIdade);
        }

        valorFinal = valorFinal.multiply(taxaDeRisco);

    return valorFinal.setScale(2, RoundingMode.HALF_UP);
    }

    public ApoliceResponse gerarApolice (Long funcionarioId, ApoliceRequest request){
        Seguro seguro = seguroRepository.findById(request.idSeguro())
                .orElseThrow(
                        () -> new SeguroNaoEncontradoException(SEGURO_NAO_ENCONTRADO.getMessage()));


        Cliente cliente = clienteRepository.findById(request.idCliente())
                .orElseThrow(
                        () -> new ClienteNaoEncontradoException(CLIENTE_NAO_ENCONTRADO.getMessage()));

        acesso.validarAcesso(funcionarioId);

        BigDecimal valorFinal = calcularValorFinal(seguro.getValorPremioBase(),
                cliente.getDataNascimento());

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
