package br.com.banksecure.app.service;

import br.com.banksecure.app.domain.Seguro;
import br.com.banksecure.app.dto.request.SeguroRequest;
import br.com.banksecure.app.dto.request.SeguroUpdateRequest;
import br.com.banksecure.app.dto.response.SeguroResponse;
import br.com.banksecure.app.enums.TipoSeguroeBem;
import br.com.banksecure.app.exception.SeguroExistenteException;
import br.com.banksecure.app.exception.SeguroNaoEncontradoException;
import br.com.banksecure.app.mapper.SeguroMapper;
import br.com.banksecure.app.repository.SeguroRepository;
import br.com.banksecure.app.util.ValidarAcesso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static br.com.banksecure.app.enums.ErrorMessage.SEGURO_JA_EXISTE;
import static br.com.banksecure.app.enums.ErrorMessage.SEGURO_NAO_ENCONTRADO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeguroServiceTest {

    @InjectMocks
    private SeguroService service;
    @Mock
    private SeguroRepository repository;
    @Mock
    private SeguroMapper mapper;
    @Mock
    private ValidarAcesso acesso;

    private Seguro seguro;
    private SeguroRequest request;
    private SeguroResponse response;
    private SeguroUpdateRequest update;

    @BeforeEach
    void setUp() {
        seguro = Seguro.builder()
                .id(1L)
                .titulo("Seguro Residencial")
                .coberturaMinima("Incêndio, Roubo")
                .valorPremioBase(new BigDecimal("1500.00"))
                .tipo(TipoSeguroeBem.RESIDENCIAL)
                .build();

        request = SeguroRequest.builder()
                .titulo("Seguro Residencial")
                .coberturaMinima("Incêndio, Roubo")
                .valorPremioBase(new BigDecimal("1500.00"))
                .tipo(TipoSeguroeBem.RESIDENCIAL)
                .build();

        response = SeguroResponse.builder()
                .id(1L)
                .titulo("Seguro Residencial")
                .coberturaMinima("Incêndio, Roubo")
                .valorPremioBase(new BigDecimal("1500.00"))
                .tipo(TipoSeguroeBem.RESIDENCIAL)
                .build();

        update = SeguroUpdateRequest.builder()
                .titulo("Seguro Apartamento")
                .coberturaMinima("Incêndio e roubo.")
                .valorPremioBase(new BigDecimal("180.00"))
                .build();
    }

    @Test
    void deveCadastrarSeguroComSucesso() {
        doNothing().when(acesso).validarAcesso(1L);
        when(repository.existsByTitulo(request.titulo())).thenReturn(false);
        when(mapper.converterParaEntity(request)).thenReturn(seguro);
        when(repository.save(seguro)).thenReturn(seguro);
        when(mapper.converterParaResponse(seguro)).thenReturn(response);

        SeguroResponse resultado = service.cadastrar(request, 1L);

        assertEquals(response, resultado);
        assertNotNull(seguro);

        verify(repository).save(seguro);
        verify(repository).existsByTitulo(request.titulo());
    }

    @Test
    void deveLancarExcecaoSeSeguroCadastrado() {
        doNothing().when(acesso).validarAcesso(1L);
        when(repository.existsByTitulo(request.titulo())).thenReturn(true);

        SeguroExistenteException ex = assertThrows(
                        SeguroExistenteException.class,
                        () -> service.cadastrar(request, 1L));

        assertEquals(SEGURO_JA_EXISTE.getMessage(), ex.getMessage());
    }

    @Test
    void deveListarTodosSeguros(){
        when(repository.findAll()).thenReturn(List.of(seguro));
        when(mapper.converterParaResponse(seguro)).thenReturn(response);

        List<SeguroResponse> resultado = service.listarTodos();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(repository).findAll();
    }

    @Test
    void deveAtualizarSeguroComSucesso(){
        doNothing().when(acesso).validarAcesso(1L);

        when(repository.findById(seguro.getId())).thenReturn(Optional.of(seguro));
        when(repository.save(seguro)).thenReturn(seguro);
        when(mapper.converterParaResponse(seguro)).thenReturn(response);

        SeguroResponse resultado = service.atualizar(seguro.getId(), update, 1L);

        assertNotNull(resultado);
        assertEquals(response, resultado);

        verify(repository).save(seguro);
    }

    @Test
    void deveLancarExcecaoSeguroNaoEncontrado(){
        doNothing().when(acesso).validarAcesso(1L);

        when(repository.findById(seguro.getId())).thenReturn(Optional.empty());

        SeguroNaoEncontradoException ex = assertThrows(
                SeguroNaoEncontradoException.class,
                () -> service.atualizar(seguro.getId(), update, 1L));

        assertEquals(SEGURO_NAO_ENCONTRADO.getMessage(), ex.getMessage());
    }

    @Test
    void deveExcluirSeguroComSucesso(){
        doNothing().when(acesso).validarAcesso(1L);

        when(repository.findById(seguro.getId())).thenReturn(Optional.of(seguro));
        doNothing().when(repository).delete(seguro);

        service.excluir(seguro.getId(), 1L);

        verify(repository).delete(seguro);
    }

    @Test
    void deveLancarExcecaoAoExcluirSeguroNaoEncontrado(){
        doNothing().when(acesso).validarAcesso(1L);

        when(repository.findById(seguro.getId())).thenReturn(Optional.empty());

        SeguroNaoEncontradoException ex = assertThrows(
                SeguroNaoEncontradoException.class,
                () -> service.excluir(seguro.getId(), 1L));

        assertEquals(SEGURO_NAO_ENCONTRADO.getMessage(), ex.getMessage());
    }

}