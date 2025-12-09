package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Apolice;
import br.com.banksecure.app.dto.response.ApoliceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApoliceMapper {

    @Mapping(target = "idCliente", source = "cliente.id")
    @Mapping(target = "idSeguro", source = "seguro.id")
    ApoliceResponse converterParaResponse(Apolice apolice);

}