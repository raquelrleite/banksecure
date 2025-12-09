package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Bem;
import br.com.banksecure.app.dto.request.BemRequest;
import br.com.banksecure.app.dto.response.BemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface BemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    Bem converterParaEntity(BemRequest request);

    BemResponse converterParaResponse(Bem bem);
}
