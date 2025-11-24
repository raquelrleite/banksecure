package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Funcionario;
import br.com.banksecure.app.dto.request.FuncionarioRequest;
import br.com.banksecure.app.dto.response.FuncionarioResponse;
import br.com.banksecure.app.dto.response.LoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FuncionarioMapper {
    Funcionario converterParaEntity(FuncionarioRequest request);
    FuncionarioResponse converterParaResponse(Funcionario funcionario);
    LoginResponse converterParaLogin(Funcionario funcionario);
}