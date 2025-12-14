package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.Bem;
import br.com.banksecure.app.dto.request.BemRequest;
import br.com.banksecure.app.dto.response.BemResponse;
import br.com.banksecure.app.enums.TipoSeguroeBem;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-14T02:07:14-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class BemMapperImpl implements BemMapper {

    @Override
    public Bem converterParaEntity(BemRequest request) {
        if ( request == null ) {
            return null;
        }

        Bem.BemBuilder bem = Bem.builder();

        if ( request.tipo() != null ) {
            bem.tipo( Enum.valueOf( TipoSeguroeBem.class, request.tipo() ) );
        }
        bem.descricao( request.descricao() );

        return bem.build();
    }

    @Override
    public BemResponse converterParaResponse(Bem bem) {
        if ( bem == null ) {
            return null;
        }

        BemResponse.BemResponseBuilder bemResponse = BemResponse.builder();

        return bemResponse.build();
    }
}
