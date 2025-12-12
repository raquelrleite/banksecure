package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.Apolice;
import br.com.banksecure.app.dto.response.ApoliceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApoliceMapper {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "seguro.id", target = "seguroId")
    ApoliceResponse converterParaResponse(Apolice apolice);

}
