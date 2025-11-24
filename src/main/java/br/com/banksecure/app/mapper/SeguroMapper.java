package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Seguro;
import br.com.banksecure.app.dto.request.SeguroRequest;
import br.com.banksecure.app.dto.response.SeguroResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeguroMapper {

    Seguro converterParaEntity(SeguroRequest request);
    SeguroResponse converterParaResponse(Seguro seguro);
}
