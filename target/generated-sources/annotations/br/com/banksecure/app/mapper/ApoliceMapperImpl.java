package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Apolice;
import br.com.banksecure.app.domain.entity.Cliente;
import br.com.banksecure.app.domain.entity.Seguro;
import br.com.banksecure.app.dto.response.ApoliceResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-08T20:56:18-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class ApoliceMapperImpl implements ApoliceMapper {

    @Override
    public ApoliceResponse converterParaResponse(Apolice apolice) {
        if ( apolice == null ) {
            return null;
        }

        Long idCliente = null;
        Long idSeguro = null;
        Long id = null;
        BigDecimal valorFinal = null;
        LocalDate inicioVigencia = null;
        LocalDate fimVigencia = null;

        idCliente = apoliceClienteId( apolice );
        idSeguro = apoliceSeguroId( apolice );
        id = apolice.getId();
        valorFinal = apolice.getValorFinal();
        inicioVigencia = apolice.getInicioVigencia();
        fimVigencia = apolice.getFimVigencia();

        ApoliceResponse apoliceResponse = new ApoliceResponse( id, idCliente, idSeguro, valorFinal, inicioVigencia, fimVigencia );

        return apoliceResponse;
    }

    private Long apoliceClienteId(Apolice apolice) {
        Cliente cliente = apolice.getCliente();
        if ( cliente == null ) {
            return null;
        }
        return cliente.getId();
    }

    private Long apoliceSeguroId(Apolice apolice) {
        Seguro seguro = apolice.getSeguro();
        if ( seguro == null ) {
            return null;
        }
        return seguro.getId();
    }
}
