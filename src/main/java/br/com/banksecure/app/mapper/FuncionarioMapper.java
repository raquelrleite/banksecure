package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Funcionario;
import br.com.banksecure.app.dto.request.FuncionarioRequest;
import br.com.banksecure.app.dto.response.FuncionarioResponse;
import br.com.banksecure.app.dto.response.LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface FuncionarioMapper {
    @Mapping(target = "id", ignore = true)
    Funcionario converterParaEntity(FuncionarioRequest request);

    FuncionarioResponse converterParaResponse(Funcionario funcionario);

    LoginResponse converterParaLogin(Funcionario funcionario);
}