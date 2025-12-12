package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.Bem;
import br.com.banksecure.app.dto.request.BemRequest;
import br.com.banksecure.app.dto.response.BemResponse;
import br.com.banksecure.app.enums.TipoSeguroeBem;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-12T18:09:27-0300",
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

        if ( request.tipo() != null ) {
            bem.setTipo( Enum.valueOf( TipoSeguroeBem.class, request.tipo() ) );
        }
        bem.setDescricao( request.descricao() );

        return bem;
    }

    @Override
    public BemResponse converterParaResponse(Bem bem) {
        if ( bem == null ) {
            return null;
        }

        Long id = null;
        String tipo = null;
        String descricao = null;

        id = bem.getId();
        if ( bem.getTipo() != null ) {
            tipo = bem.getTipo().name();
        }
        descricao = bem.getDescricao();

        BemResponse bemResponse = new BemResponse( id, tipo, descricao );

        return bemResponse;
    }
}
