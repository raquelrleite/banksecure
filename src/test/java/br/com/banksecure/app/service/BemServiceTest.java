package br.com.banksecure.app.service;

import br.com.banksecure.app.domain.Bem;
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

import java.util.List;
import java.util.Optional;

import static br.com.banksecure.app.builder.BemBuilder.umBem;
import static br.com.banksecure.app.builder.ClienteBuilder.umCliente;
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

    @BeforeEach
    void setUp() {
        doNothing().when(acesso).validarAcesso(1L);

        bem = umBem().withId(1L).build();

        request = BemRequest.builder()
                .clienteId(bem.getCliente().getId())
                .tipo(bem.getTipo())
                .descricao(bem.getDescricao())
                .build();

        response = BemResponse.builder()
                .id(bem.getId())
                .tipo(bem.getTipo())
                .descricao(bem.getDescricao())
                .build();
    }

    @Test
    void deveCadastrarBemComSucesso() {
        when(clienteRepository.findById(bem.getCliente().getId())).thenReturn(Optional.of(bem.getCliente()));
        when(mapper.converterParaEntity(request)).thenReturn(bem);
        when(repository.save(bem)).thenReturn(bem);
        when(mapper.converterParaResponse(bem)).thenReturn(response);

        BemResponse resultado = service.cadastrar(request, 1L);

        assertNotNull(resultado);
        assertEquals(response.id(), resultado.id());

        verify(repository).save(bem);
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        BemRequest requestComIdInexistente = BemRequest.builder()
                .clienteId(999L)
                .tipo(TipoSeguroeBem.AUTO)
                .build();

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        ClienteNaoEncontradoException ex = assertThrows(
                ClienteNaoEncontradoException.class,
                () -> service.cadastrar(requestComIdInexistente, 1L));

        assertEquals(CLIENTE_NAO_ENCONTRADO.getMessage(), ex.getMessage());
    }

    @Test
    void deveAtualizarBemComSucesso() {
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
    void deveLancarExcecaoQuandoNaoEncontrarBemAoAtualizar() {
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
    void deveListarBensPorClienteComSucesso() {
        bem.setCliente(umCliente().withId(1L).build());

        when(repository.findByClienteId(bem.getCliente().getId())).thenReturn(List.of(bem));
        when(mapper.converterParaResponse(bem)).thenReturn(response);

        List<BemResponse> resultado = service.listarPorCliente(bem.getCliente().getId(), 1L);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void deveListarBensSemFiltrarPorClienteComSucesso() {
        when(repository.findAll()).thenReturn(List.of(bem));
        when(mapper.converterParaResponse(bem)).thenReturn(response);

        List<BemResponse> resultado = service.listarPorCliente(null, 1L);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }
}
