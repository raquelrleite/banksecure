package br.com.banksecure.app.util;

import br.com.banksecure.app.exception.AcessoNegadoException;
import br.com.banksecure.app.repository.FuncionarioRepository;
import org.springframework.stereotype.Component;

import static br.com.banksecure.app.enums.ErrorMessage.ACESSO_NEGADO;

@Component
public class ValidarAcesso {

    private final FuncionarioRepository funcRepository;

    public ValidarAcesso(FuncionarioRepository funcRepository) {
        this.funcRepository = funcRepository;
    }

    public void validarAcesso(Long funcionarioId) {
        if (funcionarioId == null || !funcRepository.existsById(funcionarioId)) {
            throw new AcessoNegadoException(ACESSO_NEGADO.getMessage());
        }
    }
}
