package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Seguro;
import br.com.banksecure.app.dto.request.SeguroRequest;
import br.com.banksecure.app.dto.response.SeguroResponse;

public class SeguroMapper {

    public Seguro converterParaEntity(SeguroRequest request){
        Seguro seguro = new Seguro();
        seguro.setTitulo(request.titulo());
        seguro.setCoberturaMinima(request.coberturaMinima());
        seguro.setValorPremioBase(request.valorPremioBase());

        return seguro;
    }

    public SeguroResponse converterParaResponse(Seguro seguro){
        return new SeguroResponse(
                seguro.getId(),
                seguro.getTitulo(),
                seguro.getCoberturaMinima(),
                seguro.getValorPremioBase()
        );
    }
}
