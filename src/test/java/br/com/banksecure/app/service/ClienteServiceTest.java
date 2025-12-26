package br.com.banksecure.app.service;

import br.com.banksecure.app.domain.Cliente;
import br.com.banksecure.app.dto.request.ClienteRequest;
import br.com.banksecure.app.dto.response.ClienteResponse;
import br.com.banksecure.app.exception.CpfExistenteException;
import br.com.banksecure.app.exception.IdadeInvalidaException;
import br.com.banksecure.app.mapper.ClienteMapper;
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

import static br.com.banksecure.app.builder.ClienteBuilder.umCliente;
import static br.com.banksecure.app.enums.ErrorMessage.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService service;
    @Mock
    private ClienteRepository repository;
    @Mock
    private ClienteMapper mapper;
    @Mock
    private ValidarAcesso acesso;

    private Cliente cliente;
    private ClienteRequest request;
    private ClienteResponse response;

    @BeforeEach
    void setUp(){
        doNothing().when(acesso).validarAcesso(1L);
        cliente = umCliente().build();

        request = ClienteRequest.builder()
                .nome("Nicolas")
                .cpf("006.737.490-53")
                .dataNascimento(LocalDate.of(1960, 10, 10))
                .build();

        response = ClienteResponse.builder()
                .id(1L)
                .nome("Nicolas")
                .cpf("006.737.490-53")
                .dataNascimento(LocalDate.of(1960, 10, 10))
                .build();
    }

    @Test
    void deveCadastrarClienteComSucesso(){
        when(repository.existsByCpf(cliente.getCpf())).thenReturn(false);
        when(mapper.converterParaEntity(request)).thenReturn(cliente);
        when(mapper.converterParaResponse(cliente)).thenReturn(response);
        when(repository.save(cliente)).thenReturn(cliente);

        ClienteResponse resultado = service.cadastrar(request, 1L);

        assertNotNull(resultado);

        verify(repository).save(cliente);
    }

    @Test
    void deveLancarExcecaoSeClienteCadastrado(){
        when(repository.existsByCpf(cliente.getCpf())).thenReturn(true);

        CpfExistenteException ex =
                assertThrows(
                        CpfExistenteException.class,
                () -> service.cadastrar(request, 1L));

        assertEquals(CPF_JA_EXISTE.getMessage(), ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeClienteMenorDeIdade(){
        ClienteRequest menorIdade = ClienteRequest.builder()
                .nome("Nicolas")
                .cpf("006.737.490-53")
                .dataNascimento(LocalDate.of(2020, 4, 10))
                .build();


        Cliente clienteMenor = umCliente()
                .withCpf("212.660.780-10")
                .withDataNascimento(LocalDate.of(2020, 4, 10))
                .build();


        when(repository.existsByCpf(menorIdade.cpf())).thenReturn(false);
        when(mapper.converterParaEntity(menorIdade)).thenReturn(clienteMenor);

        IdadeInvalidaException ex =
                assertThrows(
                        IdadeInvalidaException.class,
                        () -> service.cadastrar(menorIdade, 1L));

        assertEquals(MENOR_IDADE.getMessage(), ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeClienteComMaisDe120Anos(){
        ClienteRequest superIdoso = ClienteRequest.builder()
                .nome("Nicolas")
                .cpf("006.737.490-53")
                .dataNascimento(LocalDate.of(1900, 4, 10))
                .build();

        Cliente clienteIdoso = umCliente()
                .withDataNascimento(LocalDate.of(1900, 4, 10))
                .withCpf("006.737.490-53")
                .build();

        when(repository.existsByCpf(superIdoso.cpf())).thenReturn(false);
        when(mapper.converterParaEntity(superIdoso)).thenReturn(clienteIdoso);

        IdadeInvalidaException ex =
                assertThrows(
                        IdadeInvalidaException.class,
                        () -> service.cadastrar(superIdoso, 1L));

        assertEquals(MAIOR_QUE_120.getMessage(), ex.getMessage());
    }

    @Test
    void deveListarTodosClientesComSucesso() {
        when(repository.findAll()).thenReturn(List.of(cliente));
        when(mapper.converterParaResponse(cliente)).thenReturn(response);

        List<ClienteResponse> clientes = service.listarTodosClientes(1L);

        assertEquals(1, clientes.size());

        verify(repository).findAll();
    }

}
