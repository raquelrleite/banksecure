package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.Bem;
import br.com.banksecure.app.dto.request.BemRequest;
import br.com.banksecure.app.dto.response.BemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BemMapper {
    Bem converterParaEntity(BemRequest request);

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "tipo", target = "tipo")
    BemResponse converterParaResponse(Bem bem);
}
