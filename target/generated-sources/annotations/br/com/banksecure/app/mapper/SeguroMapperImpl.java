package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Seguro;
import br.com.banksecure.app.dto.request.SeguroRequest;
import br.com.banksecure.app.dto.response.SeguroResponse;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
<<<<<<< Updated upstream
    date = "2025-12-01T15:24:25-0300",
=======
    date = "2025-12-06T23:22:32-0300",
>>>>>>> Stashed changes
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class SeguroMapperImpl implements SeguroMapper {

    @Override
    public Seguro converterParaEntity(SeguroRequest request) {
        if ( request == null ) {
            return null;
        }

        Seguro seguro = new Seguro();

        seguro.setTitulo( request.titulo() );
        seguro.setCoberturaMinima( request.coberturaMinima() );
        seguro.setValorPremioBase( request.valorPremioBase() );

        return seguro;
    }

    @Override
    public SeguroResponse converterParaResponse(Seguro seguro) {
        if ( seguro == null ) {
            return null;
        }

        Long id = null;
        String titulo = null;
        String coberturaMinima = null;
        BigDecimal valorPremioBase = null;

        id = seguro.getId();
        titulo = seguro.getTitulo();
        coberturaMinima = seguro.getCoberturaMinima();
        valorPremioBase = seguro.getValorPremioBase();

        SeguroResponse seguroResponse = new SeguroResponse( id, titulo, coberturaMinima, valorPremioBase );

        return seguroResponse;
    }
}
