package br.com.banksecure.app.service;

import br.com.banksecure.app.domain.Bem;
import br.com.banksecure.app.domain.Cliente;
import br.com.banksecure.app.dto.request.BemRequest;
import br.com.banksecure.app.dto.request.BemUpdateRequest;
import br.com.banksecure.app.dto.response.BemResponse;
import br.com.banksecure.app.enums.TipoSeguroeBem;
import br.com.banksecure.app.exception.BemNaoEncontradoException;
import br.com.banksecure.app.exception.ClienteNaoEncontradoException;
import br.com.banksecure.app.mapper.BemMapper;
import br.com.banksecure.app.repository.BemRepository;
import br.com.banksecure.app.repository.ClienteRepository;
import br.com.banksecure.app.util.ValidarAcesso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.banksecure.app.enums.ErrorMessage.BEM_NAO_ENCONTRADO;
import static br.com.banksecure.app.enums.ErrorMessage.CLIENTE_NAO_ENCONTRADO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BemServiceTest {

    @InjectMocks
    private BemService service;
    @Mock
    private BemRepository repository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private BemMapper mapper;
    @Mock
    private ValidarAcesso acesso;

    private BemResponse response;
    private BemRequest request;
    private Bem bem;
    private Cliente cliente;


    @BeforeEach
    void setUp() {
        doNothing().when(acesso).validarAcesso(1L);

        request = BemRequest.builder()
                .clienteId(1L)
                .tipo(TipoSeguroeBem.AUTO)
                .descricao("Fiat Uno com escada no teto")
                .build();

        bem = Bem.builder()
                .id(1L)
                .cliente(cliente)
                .tipo(TipoSeguroeBem.AUTO)
                .descricao("Fiat Uno com escada no teto")
                .build();

        response = BemResponse.builder()
                .id(1L)
                .tipo(TipoSeguroeBem.AUTO)
                .descricao("Fiat Uno com escada no teto")
                .build();

        cliente = Cliente.builder()
                .id(1L)
                .nome("Nicolas")
                .cpf("006.737.490-53")
                .dataNascimento(LocalDate.of(1960, 10, 10))
                .build();
    }

    @Test
    void deveCadastrarBemComSucesso() {
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(mapper.converterParaEntity(request)).thenReturn(bem);
        when(repository.save(bem)).thenReturn(bem);
        when(mapper.converterParaResponse(bem)).thenReturn(response);

        BemResponse resultado = service.cadastrar(request, 1L);

        assertNotNull(resultado);
        assertEquals(response.id(), resultado.id());

        verify(repository).save(bem);
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado(){
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.empty());

        ClienteNaoEncontradoException ex = assertThrows(
                ClienteNaoEncontradoException.class,
                () -> service.cadastrar(request, 1L));

        assertEquals(CLIENTE_NAO_ENCONTRADO.getMessage(), ex.getMessage());
    }

    @Test
    void deveAtualizarBemComSucesso(){
        BemUpdateRequest update = BemUpdateRequest.builder()
                .tipo(TipoSeguroeBem.AUTO)
                .descricao("Gol quadrado")
                .build();

        when(repository.findById(bem.getId())).thenReturn(Optional.of(bem));
        when(repository.save(bem)).thenReturn(bem);
        when(mapper.converterParaResponse(bem)).thenReturn(response);

        BemResponse atualizado = service.atualizar(bem.getId(), update, 1L);

        assertNotNull(atualizado);
        assertEquals(response, atualizado);

        verify(repository).save(bem);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarBemAoAtualizar(){
        BemUpdateRequest update = BemUpdateRequest.builder()
                .tipo(TipoSeguroeBem.AUTO)
                .descricao("Gol quadrado")
                .build();

        when(repository.findById(bem.getId())).thenReturn(Optional.empty());

        BemNaoEncontradoException ex = assertThrows(
                BemNaoEncontradoException.class,
                () -> service.atualizar(bem.getId(), update, 1L));

        assertEquals(BEM_NAO_ENCONTRADO.getMessage(), ex.getMessage());
    }

    @Test
    void deveListarBensPorClienteComSucesso(){
        when(repository.findByClienteId(cliente.getId())).thenReturn(List.of(bem));
        when(mapper.converterParaResponse(bem)).thenReturn(response);

        List<BemResponse> resultado = service.listarPorCliente(cliente.getId(), 1L);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(response, resultado.get(0));
    }

    @Test
    void deveListarBensSemFiltrarPorClienteComSucesso(){
        when(repository.findAll()).thenReturn(List.of(bem));
        when(mapper.converterParaResponse(bem)).thenReturn(response);

        List<BemResponse> resultado = service.listarPorCliente(null, 1L);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(response, resultado.get(0));
    }
}