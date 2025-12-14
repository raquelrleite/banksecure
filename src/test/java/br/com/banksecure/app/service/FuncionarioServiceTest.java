package br.com.banksecure.app.service;

import br.com.banksecure.app.domain.Funcionario;
import br.com.banksecure.app.dto.request.FuncionarioRequest;
import br.com.banksecure.app.dto.request.LoginRequest;
import br.com.banksecure.app.dto.response.FuncionarioResponse;
import br.com.banksecure.app.exception.LoginInvalidoException;
import br.com.banksecure.app.exception.UsernameInvalidoException;
import br.com.banksecure.app.mapper.FuncionarioMapper;
import br.com.banksecure.app.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.banksecure.app.enums.ErrorMessage.LOGIN_INVALIDO;
import static br.com.banksecure.app.enums.ErrorMessage.USERNAME_INVALIDO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {

    @InjectMocks
    private FuncionarioService service;
    @Mock
    private FuncionarioRepository repository;
    @Mock
    private FuncionarioMapper mapper;

    private Funcionario funcionario;
    private FuncionarioRequest request;
    private FuncionarioResponse response;

    @BeforeEach
    void setUp() {
        funcionario = Funcionario.builder()
                .id(1L)
                .nome("João Silva")
                .username("joao.silva")
                .password("senha123")
                .build();

        request = FuncionarioRequest.builder()
                .nome("João Silva")
                .username("joao.silva")
                .password("senha123")
                .build();


        response = FuncionarioResponse.builder()
                .id(1L)
                .nome("João Silva")
                .username("joao.silva")
                .build();
    }

    @Test
    void deveCadastrarFuncionarioComSucesso() {
        when(mapper.converterParaEntity(request)).thenReturn(funcionario);
        when(repository.existsByUsername(request.username())).thenReturn(false);
        when(repository.save(funcionario)).thenReturn(funcionario);
        when(mapper.converterParaResponse(funcionario)).thenReturn(response);

        FuncionarioResponse resultado = service.cadastrar(request);

        assertNotNull(resultado);
        assertEquals(response.id(), resultado.id());

        verify(repository).save(funcionario);
    }

    @Test
    void deveLancarExcecaoAoCadastrarFuncionarioComUsernameExistente() {
        when(mapper.converterParaEntity(request)).thenReturn(funcionario);
        when(repository.existsByUsername(funcionario.getUsername())).thenReturn(true);

        UsernameInvalidoException ex = assertThrows(
                UsernameInvalidoException.class,
                () -> service.cadastrar(request));

        assertEquals(USERNAME_INVALIDO.getMessage(), ex.getMessage());
    }

    @Test
    void deveRealizarLoginComSucesso() {
        LoginRequest login = LoginRequest.builder()
                .username("joao.silva")
                .password("senha123")
                .build();

        when(repository.findByUsernameAndPassword(request.username(), request.password())).thenReturn(Optional.of(funcionario));
        when(mapper.converterParaResponse(funcionario)).thenReturn(response);

        FuncionarioResponse resultado = service.login(login);

        assertNotNull(resultado);
        verify(repository).findByUsernameAndPassword(request.username(), request.password());
    }

    @Test
    void deveLancarExcecaoAoRealizarLoginComDadosInvalidos() {
        LoginRequest login = LoginRequest.builder()
                .username("joao.silva")
                .password("senhaErrada")
                .build();

        when(repository.findByUsernameAndPassword(login.username(), login.password())).thenReturn(Optional.empty());

        LoginInvalidoException ex = assertThrows(
                LoginInvalidoException.class,
                () -> service.login(login));

        assertEquals(LOGIN_INVALIDO.getMessage(), ex.getMessage());
    }

}