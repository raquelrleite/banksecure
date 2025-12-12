package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.Funcionario;
import br.com.banksecure.app.dto.request.FuncionarioRequest;
import br.com.banksecure.app.dto.response.FuncionarioResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FuncionarioMapper {
    Funcionario converterParaEntity(FuncionarioRequest request);

    FuncionarioResponse converterParaResponse(Funcionario funcionario);
}