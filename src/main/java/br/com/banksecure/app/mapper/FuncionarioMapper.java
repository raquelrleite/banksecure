package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Funcionario;
import br.com.banksecure.app.dto.request.FuncionarioRequest;
import br.com.banksecure.app.dto.request.LoginRequest;
import br.com.banksecure.app.dto.response.FuncionarioResponse;
import br.com.banksecure.app.dto.response.LoginResponse;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper {

    public Funcionario converterParaEntity(FuncionarioRequest request) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(request.nome());
        funcionario.setCargo(request.cargo());
        funcionario.setUsername(request.username());
        funcionario.setPassword(request.password());
        return funcionario;
    }

    public FuncionarioResponse converterParaResponse(Funcionario funcionario) {
        return new FuncionarioResponse(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCargo(),
                funcionario.getUsername()
        );
    }

    public LoginResponse converterParaLogin(Funcionario funcionario) {
        return new LoginResponse(
                funcionario.getId()
        );
    }
}
