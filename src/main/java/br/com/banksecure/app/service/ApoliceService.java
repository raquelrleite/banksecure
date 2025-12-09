package br.com.banksecure.app.service;


import br.com.banksecure.app.domain.entity.Apolice;
import br.com.banksecure.app.domain.entity.Cliente;
import br.com.banksecure.app.domain.entity.Seguro;
import br.com.banksecure.app.dto.request.ApoliceRequest;
import br.com.banksecure.app.dto.request.RenovacaoApoliceRequest;
import br.com.banksecure.app.dto.response.ApoliceResponse;
import br.com.banksecure.app.dto.response.ApolicesAVencerResponse;
import br.com.banksecure.app.exception.ApoliceNaoEncontradaException;
import br.com.banksecure.app.exception.ClienteNaoEncontradoException;
import br.com.banksecure.app.exception.SeguroNaoEncontradoException;
import br.com.banksecure.app.mapper.ApoliceMapper;
import br.com.banksecure.app.repository.ApoliceRepository;
import br.com.banksecure.app.repository.ClienteRepository;
import br.com.banksecure.app.repository.SeguroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

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

        BigDecimal taxaFixa = seguro.getValorPremioBase().multiply(BigDecimal.valueOf(0.05));

        BigDecimal valorFinal = seguro.getValorPremioBase().add(taxaFixa);

        BigDecimal taxaDeRisco = BigDecimal.valueOf(1.10);

        int idade = Period.between(cliente.getDataNascimento(),LocalDate.now()).getYears();

        if(idade>60){
            valorFinal = valorFinal.add(BigDecimal.valueOf(100));
        }

        valorFinal = valorFinal.multiply(taxaDeRisco);


    return valorFinal.setScale(2, RoundingMode.HALF_UP);
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

    @Transactional
    public List<ApolicesAVencerResponse> listarApolicesAVencer() {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataLimite = dataAtual.plusYears(1);
        
        List<Apolice> apolices = apoliceRepository.findApolicesAVencer(dataAtual, dataLimite);
        
        return apolices.stream()
                .map(this::converterParaApolicesAVencerResponse)
                .toList();
    }

    private ApolicesAVencerResponse converterParaApolicesAVencerResponse(Apolice apolice) {
        long diasParaVencer = Period.between(LocalDate.now(), apolice.getFimVigencia()).getDays();
        
        return new ApolicesAVencerResponse(
                apolice.getId(),
                apolice.getCliente().getNome(),
                apolice.getCliente().getCpf(),
                apolice.getSeguro().getTitulo(),
                apolice.getValorFinal(),
                apolice.getFimVigencia(),
                diasParaVencer
        );
    }

    @Transactional
    public ApoliceResponse renovarApolice(RenovacaoApoliceRequest request) {
        Apolice apoliceAnterior = apoliceRepository.findById(request.idApolice())
                .orElseThrow(() -> new ApoliceNaoEncontradaException("Apólice não encontrada"));

        Cliente cliente = apoliceAnterior.getCliente();
        Seguro seguro = apoliceAnterior.getSeguro();

        // Calcula novo valor com 10% de acréscimo
        BigDecimal novoValor = apoliceAnterior.getValorFinal()
                .multiply(BigDecimal.valueOf(1.10))
                .setScale(2, RoundingMode.HALF_UP);

        Apolice novaApolice = new Apolice();
        novaApolice.setCliente(cliente);
        novaApolice.setSeguro(seguro);
        novaApolice.setValorFinal(novoValor);
        novaApolice.setInicioVigencia(LocalDate.now());
        novaApolice.setFimVigencia(LocalDate.now().plusYears(1));

        Apolice apoliceSalva = apoliceRepository.save(novaApolice);

        return mapper.converterParaResponse(apoliceSalva);
    }
}
