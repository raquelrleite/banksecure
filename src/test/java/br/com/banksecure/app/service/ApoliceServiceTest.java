package br.com.banksecure.app.service;

import br.com.banksecure.app.domain.Apolice;
import br.com.banksecure.app.domain.Bem;
import br.com.banksecure.app.domain.Cliente;
import br.com.banksecure.app.domain.Seguro;
import br.com.banksecure.app.dto.request.ApoliceRequest;
import br.com.banksecure.app.dto.request.ApoliceUpdateRequest;
import br.com.banksecure.app.dto.response.ApoliceResponse;
import br.com.banksecure.app.enums.ApoliceStatus;
import br.com.banksecure.app.enums.TipoSeguroeBem;
import br.com.banksecure.app.exception.*;
import br.com.banksecure.app.mapper.ApoliceMapper;
import br.com.banksecure.app.repository.ApoliceRepository;
import br.com.banksecure.app.repository.BemRepository;
import br.com.banksecure.app.repository.ClienteRepository;
import br.com.banksecure.app.repository.SeguroRepository;
import br.com.banksecure.app.util.ValidarAcesso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.banksecure.app.enums.ErrorMessage.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApoliceServiceTest {

    @InjectMocks
    private ApoliceService service;
    @Mock
    private ApoliceRepository repository;
    @Mock
    private SeguroRepository seguroRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ApoliceMapper mapper;
    @Mock
    private ValidarAcesso acesso;
    @Mock
    private BemRepository bemRepository;

    private Apolice apolice;
    private ApoliceRequest request;
    private ApoliceResponse response;
    private ApoliceUpdateRequest updateRequest;
    private Cliente clienteMaiorIdade;
    private Seguro seguroResidencial;
    private Seguro seguroAuto;
    private Seguro seguroVida;
    private Bem bem;

    @BeforeEach
    void setUp() {
        doNothing().when(acesso).validarAcesso(1L);

        LocalDate inicio = LocalDate.now();
        LocalDate fim = inicio.plusYears(1);

        LocalDate inicioAtualizado = LocalDate.now().minusYears(1);
        LocalDate fimAtualizado = inicioAtualizado.plusYears(1);

        apolice = Apolice.builder()
                .id(1L)
                .cliente(clienteMaiorIdade)
                .seguro(seguroResidencial)
                .bem(bem)
                .valorFinal(new BigDecimal("200.00"))
                .inicioVigencia(inicio)
                .fimVigencia(fim)
                .status(ApoliceStatus.ATIVA)
                .build();

        request = ApoliceRequest.builder()
                .clienteId(1L)
                .seguroId(1L)
                .bemId(1L)
                .build();

        response = ApoliceResponse.builder()
                .id(1L)
                .clienteId(1L)
                .seguroId(1L)
                .valorFinal(new BigDecimal("500"))
                .inicioVigencia(inicioAtualizado)
                .fimVigencia(fimAtualizado)
                .status(ApoliceStatus.ATIVA)
                .build();

        updateRequest = ApoliceUpdateRequest.builder()
                .inicioVigencia(inicioAtualizado)
                .fimVigencia(fimAtualizado)
                .status(ApoliceStatus.ATIVA)
                .build();

        clienteMaiorIdade = Cliente.builder()
                .id(1L)
                .nome("Nicolas")
                .cpf("006.737.490-53")
                .dataNascimento(LocalDate.of(1960, 10, 10))
                .build();

        seguroResidencial = Seguro.builder()
                .id(1L)
                .titulo("Seguro Residencial")
                .tipo(TipoSeguroeBem.RESIDENCIAL)
                .valorPremioBase(new BigDecimal("180.00"))
                .build();

        seguroVida = Seguro.builder()
                .id(1L)
                .titulo("Seguro de Vida")
                .tipo(TipoSeguroeBem.VIDA)
                .valorPremioBase(new BigDecimal("200.00"))
                .build();

        seguroAuto = Seguro.builder()
                .id(2L)
                .titulo("Seguro Auto")
                .tipo(TipoSeguroeBem.AUTO)
                .valorPremioBase(new BigDecimal("1200.00"))
                .build();

        bem = Bem.builder()
                .id(1L)
                .cliente(clienteMaiorIdade)
                .tipo(TipoSeguroeBem.RESIDENCIAL)
                .descricao("Rua Localhost, nº 127.0.0.1, Apto 404")
                .build();
    }

    @Test
    void deveGerarApoliceComSucesso() {
        when(clienteRepository.findById(clienteMaiorIdade.getId())).thenReturn(Optional.of(clienteMaiorIdade));
        when(seguroRepository.findById(seguroResidencial.getId())).thenReturn(Optional.of(seguroResidencial));
        when(bemRepository.findById(bem.getId())).thenReturn(Optional.of(bem));

        when(repository.save(any(Apolice.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(mapper.converterParaResponse(any(Apolice.class))).thenReturn(response);

        service.gerarApolice(request, 1L);

        ArgumentCaptor<Apolice> apoliceCaptor = ArgumentCaptor.forClass(Apolice.class);
        verify(repository).save(apoliceCaptor.capture());
        Apolice apoliceSalva = apoliceCaptor.getValue();

        assertEquals(clienteMaiorIdade, apoliceSalva.getCliente());
        assertEquals(seguroResidencial, apoliceSalva.getSeguro());
        assertEquals(bem, apoliceSalva.getBem());
        assertNotNull(apoliceSalva.getInicioVigencia());
        assertNotNull(apoliceSalva.getFimVigencia());
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarCliente() {
        when(clienteRepository.findById(clienteMaiorIdade.getId())).thenReturn(Optional.empty());

        ClienteNaoEncontradoException ex = assertThrows(
                ClienteNaoEncontradoException.class,
                () -> service.gerarApolice(request, 1L));

        assertEquals(CLIENTE_NAO_ENCONTRADO.getMessage(), ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarSeguro() {
        when(clienteRepository.findById(clienteMaiorIdade.getId())).thenReturn(Optional.of(clienteMaiorIdade));
        when(seguroRepository.findById(seguroResidencial.getId())).thenReturn(Optional.empty());

        SeguroNaoEncontradoException ex = assertThrows(
                SeguroNaoEncontradoException.class,
                () -> service.gerarApolice(request, 1L));

        assertEquals(SEGURO_NAO_ENCONTRADO.getMessage(), ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarBem() {
        when(clienteRepository.findById(clienteMaiorIdade.getId())).thenReturn(Optional.of(clienteMaiorIdade));
        when(seguroRepository.findById(seguroResidencial.getId())).thenReturn(Optional.of(seguroResidencial));
        when(bemRepository.findById(bem.getId())).thenReturn(Optional.empty());

        BemNaoEncontradoException ex = assertThrows(
                BemNaoEncontradoException.class,
                () -> service.gerarApolice(request, 1L));

        assertEquals(BEM_NAO_ENCONTRADO.getMessage(), ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeClientePossuirSeguroVida() {
        ApoliceRequest requestVida = ApoliceRequest.builder()
                .clienteId(clienteMaiorIdade.getId())
                .seguroId(seguroVida.getId())
                .bemId(null)
                .build();

        when(clienteRepository.findById(clienteMaiorIdade.getId())).thenReturn(Optional.of(clienteMaiorIdade));
        when(seguroRepository.findById(seguroVida.getId())).thenReturn(Optional.of(seguroVida));

        when(repository.existsByClienteIdAndSeguro_Tipo(clienteMaiorIdade.getId(), TipoSeguroeBem.VIDA)).thenReturn(true);

        ClientePossuiSegVidaException ex = assertThrows(
                ClientePossuiSegVidaException.class,
                () -> service.gerarApolice(requestVida, 1L));

        assertEquals(CLIENTE_POSSUI_SEGVIDA.getMessage(), ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoBemForNulo() {
        when(clienteRepository.findById(clienteMaiorIdade.getId())).thenReturn(Optional.of(clienteMaiorIdade));

        when(seguroRepository.findById(seguroResidencial.getId())).thenReturn(Optional.of(seguroResidencial));

        when(bemRepository.findById(bem.getId())).thenReturn(Optional.empty());

        BemNaoEncontradoException ex = assertThrows(
                BemNaoEncontradoException.class,
                () -> service.gerarApolice(request, 1L));

        assertEquals(BEM_NAO_ENCONTRADO.getMessage(), ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoIdClienteForDiferenteDoBem() {
        ApoliceRequest outroRequest = ApoliceRequest.builder()
                .clienteId(2L)
                .seguroId(1L)
                .bemId(1L)
                .build();

        Cliente outroCliente = Cliente.builder()
                .id(2L)
                .nome("Larissa")
                .cpf("492.970.620-32")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .build();

        when(clienteRepository.findById(2L)).thenReturn(Optional.of(outroCliente));
        when(seguroRepository.findById(seguroResidencial.getId())).thenReturn(Optional.of(seguroResidencial));
        when(bemRepository.findById(bem.getId())).thenReturn(Optional.of(bem));

        AcessoNegadoException ex = assertThrows(AcessoNegadoException.class,
                () -> service.gerarApolice(outroRequest, 1L));

        assertEquals(BEM_NAO_PERTENCE_AO_CLIENTE.getMessage(), ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoSeguroNaoTiverBem() {
        ApoliceRequest requestSemBem = ApoliceRequest.builder()
                .clienteId(1L)
                .seguroId(2L)
                .bemId(null)
                .build();

        when(seguroRepository.findById(any())).thenReturn(Optional.of(seguroAuto));
        when(clienteRepository.findById(any())).thenReturn(Optional.of(clienteMaiorIdade));

        BemNaoEncontradoException ex = assertThrows(
                BemNaoEncontradoException.class,
                () -> service.gerarApolice(requestSemBem, 1L));

        assertEquals(BEM_NAO_ENCONTRADO.getMessage(), ex.getMessage());
    }

    @Test
    void deveGerarApoliceSeguroVidaSemBem() {
        ApoliceRequest requestVida = ApoliceRequest.builder()
                .clienteId(1L)
                .seguroId(1L)
                .bemId(99L)
                .build();

        when(seguroRepository.findById(any())).thenReturn(Optional.of(seguroVida));
        when(clienteRepository.findById(any())).thenReturn(Optional.of(clienteMaiorIdade));
        when(repository.existsByClienteIdAndSeguro_Tipo(any(), eq(TipoSeguroeBem.VIDA))).thenReturn(false);

        when(repository.save(any(Apolice.class))).thenAnswer(i -> i.getArgument(0));
        when(mapper.converterParaResponse(any(Apolice.class))).thenReturn(response);

        service.gerarApolice(requestVida, 1L);

        ArgumentCaptor<Apolice> captor = ArgumentCaptor.forClass(Apolice.class);
        verify(repository).save(captor.capture());
        Apolice apoliceSalva = captor.getValue();
        assertNull(apoliceSalva.getBem());
    }

    @Test
    void deveLancarExcecaoSeBemPossuirSeguro() {
        when(clienteRepository.findById(clienteMaiorIdade.getId())).thenReturn(Optional.of(clienteMaiorIdade));
        when(seguroRepository.findById(seguroResidencial.getId())).thenReturn(Optional.of(seguroResidencial));
        when(bemRepository.findById(bem.getId())).thenReturn(Optional.of(bem));

        when(repository.existsByBemId(bem.getId())).thenReturn(true);

        BemPossuiSeguroException ex = assertThrows(
                BemPossuiSeguroException.class,
                () -> service.gerarApolice(request, 1L));

        assertEquals(BEM_POSSUI_SEGURO.getMessage(), ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoTipoDeSeguroForIncompativel() {
        ApoliceRequest outroSeguroId = ApoliceRequest.builder()
                .clienteId(1L)
                .seguroId(2L)
                .bemId(1L)
                .build();

        when(clienteRepository.findById(clienteMaiorIdade.getId())).thenReturn(Optional.of(clienteMaiorIdade));
        when(seguroRepository.findById(seguroAuto.getId())).thenReturn(Optional.of(seguroAuto));
        when(bemRepository.findById(bem.getId())).thenReturn(Optional.of(bem));

        TipoIncompativelException ex = assertThrows(
                TipoIncompativelException.class,
                () -> service.gerarApolice(outroSeguroId, 1L));

        assertEquals(TIPO_DO_BEM_INCOMPATIVEL.getMessage(), ex.getMessage());
    }

    @Test
    void deveExpirarApoliceVencida(){
        apolice.setFimVigencia(LocalDate.now().minusDays(1));
        apolice.setStatus(ApoliceStatus.ATIVA);

        when(repository.findAll()).thenReturn(List.of(apolice));

        service.apolices(1L);

        ArgumentCaptor<Apolice> captor = ArgumentCaptor.forClass(Apolice.class);

        verify(repository).save(captor.capture());

        Apolice apoliceSalva = captor.getValue();
        assertEquals(ApoliceStatus.EXPIRADA, apoliceSalva.getStatus());
    }

    @Test
    void deveReativarApoliceExpirada(){
        apolice.setFimVigencia(LocalDate.now().plusDays(30));
        apolice.setStatus(ApoliceStatus.EXPIRADA);

        when(repository.findAll()).thenReturn(List.of(apolice));

        service.apolices(1L);

        ArgumentCaptor<Apolice> captor = ArgumentCaptor.forClass(Apolice.class);

        verify(repository).save(captor.capture());

        Apolice apoliceSalva = captor.getValue();
        assertEquals(ApoliceStatus.ATIVA, apoliceSalva.getStatus());

    }

    @Test
    void deveListarApolicesAvencer() {
        Apolice apoliceAvencer = Apolice.builder()
                .id(1L)
                .fimVigencia(LocalDate.now().plusDays(15))
                .status(ApoliceStatus.ATIVA)
                .build();

        ApoliceResponse responseAvencer = ApoliceResponse.builder()
                .id(1L)
                .fimVigencia(LocalDate.now().plusDays(15))
                .status(ApoliceStatus.ATIVA)
                .build();

        when(repository.findAll()).thenReturn(List.of(apoliceAvencer));
        when(mapper.converterParaResponse(apoliceAvencer)).thenReturn(responseAvencer);

        List<ApoliceResponse> resultado = service.apolicesAvencer(1L);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());

        verify(repository).findAll();
    }

    @Test
    void deveRenovarApolice() {
        apolice.setSeguro(seguroResidencial);
        apolice.setCliente(clienteMaiorIdade);
        apolice.setBem(bem);
        apolice.setInicioVigencia(LocalDate.now().minusYears(2));
        apolice.setFimVigencia(LocalDate.now().minusYears(1));
        apolice.setStatus(ApoliceStatus.ATIVA);

        when(repository.findById(apolice.getId())).thenReturn(Optional.of(apolice));

        when(repository.save(any(Apolice.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(mapper.converterParaResponse(any(Apolice.class))).thenReturn(response);

        service.renovar(1L, 1L);

        ArgumentCaptor<Apolice> captor = ArgumentCaptor.forClass(Apolice.class);

        verify(repository, times(2)).save(captor.capture());

        List<Apolice> apolicesSalvas = captor.getAllValues();
        Apolice antigaAtualizada = apolicesSalvas.get(0);
        Apolice novaCriada = apolicesSalvas.get(1);

        assertEquals(apolice.getId(), antigaAtualizada.getId());
        assertEquals(ApoliceStatus.RENOVADA, antigaAtualizada.getStatus());

        assertEquals(ApoliceStatus.ATIVA, novaCriada.getStatus());
        assertNotEquals(antigaAtualizada.getId(), novaCriada.getId());
        assertEquals(apolice.getCliente(), novaCriada.getCliente());

        assertTrue(novaCriada.getInicioVigencia().isAfter(apolice.getInicioVigencia()));

    }

    @Test
    void deveLancarExcecaoQuandoRenovarComApoliceNaoEncontrada() {
        when(repository.findById(apolice.getId())).thenReturn(Optional.empty());

        ApoliceNaoEncontradaException ex = assertThrows(
                ApoliceNaoEncontradaException.class,
                () -> service.renovar(1L, 1L));

        assertEquals(APOLICE_NAO_ENCONTRADA.getMessage(), ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoTentarRenovarApoliceCancelada(){
        apolice.setStatus(ApoliceStatus.CANCELADA);
        when(repository.findById(apolice.getId())).thenReturn(Optional.of(apolice));

        RegraApoliceException ex = assertThrows(
                RegraApoliceException.class,
                () -> service.renovar(1L, 1L));

        assertEquals(APOLICE_NAO_PODE_SER_RENOVADA.getMessage() + ApoliceStatus.CANCELADA, ex.getMessage());
    }

    @Test
    void deveAtualizarApolice() {
        when(repository.findById(1L)).thenReturn(Optional.of(apolice));
        when(repository.save(apolice)).thenReturn(apolice);
        when(mapper.converterParaResponse(apolice)).thenReturn(response);

        ApoliceResponse atualizado = service.atualizar(1L, updateRequest, 1L);

        assertEquals(response, atualizado);
        verify(repository).save(apolice);
    }

    @Test
    void deveLancarExcecaoQuandoApoliceNaoExistirAoAtualizar() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ApoliceNaoEncontradaException ex = assertThrows(
                ApoliceNaoEncontradaException.class,
                () -> service.atualizar(1L, updateRequest, 1L));

        assertEquals(APOLICE_NAO_ENCONTRADA.getMessage(), ex.getMessage());
    }

    @Test
    void deveListarTodasApolices() {
        when(repository.findAll()).thenReturn(List.of(apolice));
        when(mapper.converterParaResponse(apolice)).thenReturn(response);

        List<ApoliceResponse> apolices = service.apolices(1L);

        assertEquals(1, apolices.size());

        verify(repository).findAll();
    }

    @Test
    void deveCancelarApoliceComSucesso() {
        when(repository.findById(apolice.getId())).thenReturn(Optional.of(apolice));

        ArgumentCaptor<Apolice> captor = ArgumentCaptor.forClass(Apolice.class);
        when(repository.save(any(Apolice.class))).thenAnswer(i -> i.getArgument(0));

        service.cancelar(1L, 1L);

        verify(repository).save(captor.capture());
        Apolice apoliceSalva = captor.getValue();

        assertEquals(ApoliceStatus.CANCELADA, apoliceSalva.getStatus());
        verify(repository).save(apolice);
    }

    @Test
    void deveLancarExcecaoQuandoApoliceNaoExistirAoCancelar() {
        when(repository.findById(apolice.getId())).thenReturn(Optional.empty());

        ApoliceNaoEncontradaException ex = assertThrows(
                ApoliceNaoEncontradaException.class,
                () -> service.cancelar(1L, 1L));

        assertEquals(APOLICE_NAO_ENCONTRADA.getMessage(), ex.getMessage());
    }
}