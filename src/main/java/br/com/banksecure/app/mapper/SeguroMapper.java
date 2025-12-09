package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Seguro;
import br.com.banksecure.app.dto.request.SeguroRequest;
import br.com.banksecure.app.dto.response.SeguroResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeguroMapper {
    @Mapping(target = "id", ignore = true)
    Seguro converterParaEntity(SeguroRequest request);

    SeguroResponse converterParaResponse(Seguro seguro);
}
