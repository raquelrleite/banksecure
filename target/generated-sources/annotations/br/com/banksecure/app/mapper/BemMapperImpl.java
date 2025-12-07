package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Bem;
import br.com.banksecure.app.dto.request.BemRequest;
import br.com.banksecure.app.dto.response.BemResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-06T23:22:32-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class BemMapperImpl implements BemMapper {

    @Override
    public Bem converterParaEntity(BemRequest request) {
        if ( request == null ) {
            return null;
        }

        Bem bem = new Bem();

        bem.setTipoBem( request.tipoBem() );
        bem.setDescricao( request.descricao() );

        return bem;
    }

    @Override
    public BemResponse converterParaResponse(Bem bem) {
        if ( bem == null ) {
            return null;
        }

        Long id = null;
        String tipoBem = null;
        String descricao = null;

        id = bem.getId();
        tipoBem = bem.getTipoBem();
        descricao = bem.getDescricao();

        BemResponse bemResponse = new BemResponse( id, tipoBem, descricao );

        return bemResponse;
    }
}
