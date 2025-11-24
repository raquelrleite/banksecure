package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Cliente;
import br.com.banksecure.app.dto.request.ClienteRequest;
import br.com.banksecure.app.dto.response.ClienteResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    Cliente converterParaEntity(ClienteRequest request);
    ClienteResponse converterParaResponse(Cliente cliente);
}
