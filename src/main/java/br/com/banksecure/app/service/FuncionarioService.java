package br.com.banksecure.app.service;

import br.com.banksecure.app.domain.Funcionario;
import br.com.banksecure.app.dto.request.FuncionarioRequest;
import br.com.banksecure.app.dto.request.LoginRequest;
import br.com.banksecure.app.dto.response.FuncionarioResponse;
import br.com.banksecure.app.exception.LoginInvalidoException;
import br.com.banksecure.app.exception.UsernameInvalidoException;
import br.com.banksecure.app.mapper.FuncionarioMapper;
import br.com.banksecure.app.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.banksecure.app.enums.ErrorMessage.LOGIN_INVALIDO;
import static br.com.banksecure.app.enums.ErrorMessage.USERNAME_INVALIDO;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final FuncionarioMapper mapper;

    public FuncionarioService(FuncionarioRepository repository, FuncionarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public FuncionarioResponse cadastrar(FuncionarioRequest request) {
        Funcionario funcionario = mapper.converterParaEntity(request);

        if (repository.existsByUsername(request.username())) {
            throw new UsernameInvalidoException(USERNAME_INVALIDO.getMessage());
        }

        Funcionario funcionarioSalvo = repository.save(funcionario);

        return mapper.converterParaResponse(funcionarioSalvo);
    }

    public FuncionarioResponse login(LoginRequest request) {

        Funcionario funcionario = repository.
                findByUsernameAndPassword(request.username(), request.password())
                .orElseThrow(
                        () -> new LoginInvalidoException(LOGIN_INVALIDO.getMessage()));

        return mapper.converterParaResponse(funcionario);
    }
}

