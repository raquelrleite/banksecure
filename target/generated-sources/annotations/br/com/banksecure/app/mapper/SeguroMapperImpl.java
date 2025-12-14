package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.Seguro;
import br.com.banksecure.app.dto.request.SeguroRequest;
import br.com.banksecure.app.dto.response.SeguroResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-14T02:07:14-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class SeguroMapperImpl implements SeguroMapper {

    @Override
    public Seguro converterParaEntity(SeguroRequest request) {
        if ( request == null ) {
            return null;
        }

        Seguro.SeguroBuilder seguro = Seguro.builder();

        seguro.titulo( request.titulo() );
        seguro.coberturaMinima( request.coberturaMinima() );
        seguro.valorPremioBase( request.valorPremioBase() );
        seguro.tipo( request.tipo() );

        return seguro.build();
    }

    @Override
    public SeguroResponse converterParaResponse(Seguro seguro) {
        if ( seguro == null ) {
            return null;
        }

        SeguroResponse.SeguroResponseBuilder seguroResponse = SeguroResponse.builder();

        return seguroResponse.build();
    }
}
