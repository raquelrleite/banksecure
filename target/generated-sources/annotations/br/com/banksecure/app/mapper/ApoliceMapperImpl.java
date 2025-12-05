package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Apolice;
import br.com.banksecure.app.dto.response.ApoliceResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-05T00:12:01-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class ApoliceMapperImpl implements ApoliceMapper {

    @Override
    public ApoliceResponse converterParaResponse(Apolice apolice) {
        if ( apolice == null ) {
            return null;
        }

        Long id = null;
        BigDecimal valorFinal = null;
        LocalDate inicioVigencia = null;
        LocalDate fimVigencia = null;

        id = apolice.getId();
        valorFinal = apolice.getValorFinal();
        inicioVigencia = apolice.getInicioVigencia();
        fimVigencia = apolice.getFimVigencia();

        Long idCliente = null;
        Long idSeguro = null;

        ApoliceResponse apoliceResponse = new ApoliceResponse( id, idCliente, idSeguro, valorFinal, inicioVigencia, fimVigencia );

        return apoliceResponse;
    }
}
