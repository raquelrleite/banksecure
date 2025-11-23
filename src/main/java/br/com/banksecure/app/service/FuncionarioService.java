package br.com.banksecure.app.service;

import br.com.banksecure.app.domain.entity.Funcionario;
import br.com.banksecure.app.dto.request.FuncionarioRequest;
import br.com.banksecure.app.dto.request.LoginRequest;
import br.com.banksecure.app.dto.response.FuncionarioResponse;
import br.com.banksecure.app.exception.LoginInvalidoException;
import br.com.banksecure.app.exception.UsernameInvalidoException;
import br.com.banksecure.app.mapper.FuncionarioMapper;
import br.com.banksecure.app.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import static br.com.banksecure.app.enums.ErrorMessage.LOGIN_INVALIDO;
import static br.com.banksecure.app.enums.ErrorMessage.USERNAME_INVALIDO;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper mapper;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, FuncionarioMapper mapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.mapper = mapper;
    }

    public FuncionarioResponse cadastrarFuncionario(FuncionarioRequest request) {
        Funcionario funcionario = mapper.converterParaEntity(request);

        if (funcionarioRepository.existsByUsername(request.username())) {
            throw new UsernameInvalidoException(USERNAME_INVALIDO.getMessage());
        }

        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);

        return mapper.converterParaResponse(funcionarioSalvo);
    }

    public FuncionarioResponse login(LoginRequest request) {

        Funcionario funcionario = funcionarioRepository.findByUsernameAndPassword(
                        request.username(),
                        request.password())
                .orElseThrow(
                        () -> new LoginInvalidoException(LOGIN_INVALIDO.getMessage()));

        return mapper.converterParaResponse(funcionario);
    }
}
