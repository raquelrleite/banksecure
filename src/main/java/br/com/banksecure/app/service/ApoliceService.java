package br.com.banksecure.app.service;


import br.com.banksecure.app.domain.Apolice;
import br.com.banksecure.app.domain.Bem;
import br.com.banksecure.app.domain.Cliente;
import br.com.banksecure.app.domain.Seguro;
import br.com.banksecure.app.dto.request.ApoliceRequest;
import br.com.banksecure.app.dto.request.ApoliceUpdateRequest;
import br.com.banksecure.app.dto.response.ApoliceResponse;
import br.com.banksecure.app.enums.TipoSeguroeBem;
import br.com.banksecure.app.exception.*;
import br.com.banksecure.app.mapper.ApoliceMapper;
import br.com.banksecure.app.repository.ApoliceRepository;
import br.com.banksecure.app.repository.BemRepository;
import br.com.banksecure.app.repository.ClienteRepository;
import br.com.banksecure.app.repository.SeguroRepository;
import br.com.banksecure.app.util.ValidarAcesso;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static br.com.banksecure.app.enums.ErrorMessage.*;

@Service
public class ApoliceService {

    private final ApoliceRepository repository;
    private final SeguroRepository seguroRepository;
    private final ClienteRepository clienteRepository;
    private final ApoliceMapper mapper;
    private final ValidarAcesso acesso;
    private final BemRepository bemRepository;


    public ApoliceService(ApoliceRepository repository, SeguroRepository seguroRepository, ClienteRepository clienteRepository, ApoliceMapper mapper, ValidarAcesso acesso, BemRepository bemRepository) {
        this.repository = repository;
        this.seguroRepository = seguroRepository;
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
        this.acesso = acesso;
        this.bemRepository = bemRepository;
    }

    public ApoliceResponse gerarApolice (ApoliceRequest request, Long funcionarioId){
        acesso.validarAcesso(funcionarioId);

        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(
                        () -> new ClienteNaoEncontradoException(CLIENTE_NAO_ENCONTRADO.getMessage()));

        Seguro seguro = seguroRepository.findById(request.seguroId())
                .orElseThrow(
                        () -> new SeguroNaoEncontradoException(SEGURO_NAO_ENCONTRADO.getMessage()));


        Bem bem = null;
        if (request.bemId() != null) {
            bem = bemRepository.findById(request.bemId())
                    .orElseThrow(() -> new BemNaoEncontradoException(BEM_NAO_ENCONTRADO.getMessage()));
        }
        if (seguro.getTipo() == TipoSeguroeBem.VIDA) {
            if (repository.existsByClienteIdAndSeguro_Tipo(request.clienteId(), TipoSeguroeBem.VIDA)) {
                throw new ClientePossuiSegVidaException(CLIENTE_POSSUI_SEGVIDA.getMessage());
            }
            bem = null;
        } else {
            if (bem == null) {
                throw new BemNaoEncontradoException(BEM_NAO_ENCONTRADO.getMessage());
            }
            if (!bem.getCliente().getId().equals(cliente.getId())) {
                throw new AcessoNegadoException(BEM_NAO_PERTENCE_AO_CLIENTE.getMessage());
            }
            if (repository.existsByBemId(bem.getId())) {
                throw new BemPossuiSeguroException(BEM_POSSUI_SEGURO.getMessage());
            }
            if (seguro.getTipo() != bem.getTipo()) {
                throw new TipoIncompativelException(TIPO_DO_BEM_INCOMPATIVEL.getMessage());
            }
        }

        BigDecimal valorFinal = calcularValorFinal(seguro, cliente);

        Apolice apolice = new Apolice();
        apolice.setCliente(cliente);
        apolice.setSeguro(seguro);
        apolice.setBem(bem);
        apolice.setValorFinal(valorFinal);

        apolice.setInicioVigencia(LocalDate.now());
        apolice.setFimVigencia(LocalDate.now().plusYears(1));

        Apolice apoliceSalva = repository.save(apolice);

        return mapper.converterParaResponse(apoliceSalva);

    }

    private BigDecimal calcularValorFinal(Seguro seguro, Cliente cliente){

        BigDecimal taxaFixa = new BigDecimal("0.05");
        BigDecimal taxaDeRisco = new BigDecimal("1.10");
        BigDecimal taxaPorIdade = new BigDecimal("100");

        BigDecimal valorBase = seguro.getValorPremioBase();

        BigDecimal valorInicial = valorBase.add(valorBase.multiply(taxaFixa));

        int idade = Period.between(cliente.getDataNascimento(), LocalDate.now()).getYears();

        if (idade > 60) {
            valorInicial = valorInicial.add(taxaPorIdade);
        } return valorInicial.multiply(taxaDeRisco).setScale(2, RoundingMode.HALF_UP);
    }

    public List<ApoliceResponse> apolicesAvencer(Long funcionarioId) {
        acesso.validarAcesso(funcionarioId);

        LocalDate hoje = LocalDate.now().minusDays(1);
        LocalDate dataLimite = hoje.plusDays(60);

        return repository.findAll()
                .stream()
                .map(mapper::converterParaResponse)
                .filter(apolice
                        -> apolice.fimVigencia().isAfter(hoje) &&
                        apolice.fimVigencia().isBefore(dataLimite))
                .toList();
    }

    @Transactional
    public ApoliceResponse renovar(Long funcionarioId, Long apoliceId) {

        acesso.validarAcesso(funcionarioId);

        Apolice apoliceAntiga = repository.findById(apoliceId)
                .orElseThrow(
                        () -> new ApoliceNaoEncontradaException(APOLICE_NAO_ENCONTRADA.getMessage()));

        Apolice novaApolice = new Apolice();

        novaApolice.setCliente(apoliceAntiga.getCliente());
        novaApolice.setSeguro(apoliceAntiga.getSeguro());

        novaApolice.setInicioVigencia(LocalDate.now());
        novaApolice.setFimVigencia(LocalDate.now().plusYears(1));

        BigDecimal novoValor = calcularValorFinal(apoliceAntiga.getSeguro(), apoliceAntiga.getCliente());
        novaApolice.setValorFinal(novoValor);

        Apolice apoliceSalva = repository.save(novaApolice);

        return mapper.converterParaResponse(apoliceSalva);
    }

    @Transactional
    public ApoliceResponse atualizar(Long id, ApoliceUpdateRequest request, Long funcionarioId) {
        acesso.validarAcesso(funcionarioId);

        var apolice = repository.findById(id)
                .orElseThrow(
                        () -> new ApoliceNaoEncontradaException(APOLICE_NAO_ENCONTRADA.getMessage()));

        if (request.inicioVigencia() != null) {
            apolice.setInicioVigencia(request.inicioVigencia());
        }

        if (request.fimVigencia() != null) {
            apolice.setFimVigencia(request.fimVigencia());
        }

        repository.save(apolice);

        return mapper.converterParaResponse(apolice);
    }

    @Transactional
    public void excluir(Long funcionarioId, Long apoliceId) {
        acesso.validarAcesso(funcionarioId);
        repository.deleteById(apoliceId);
    }


    public List<ApoliceResponse> apolices(Long funcionarioId) {
        acesso.validarAcesso(funcionarioId);
       return repository.findAll()
               .stream()
               .map(mapper::converterParaResponse)
               .toList();
    }
}
