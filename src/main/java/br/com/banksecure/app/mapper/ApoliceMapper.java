package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Apolice;
import br.com.banksecure.app.dto.response.ApoliceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApoliceMapper {


    ApoliceResponse converterParaResponse(Apolice apolice);



}
