package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.Apolice;
import br.com.banksecure.app.domain.Cliente;
import br.com.banksecure.app.domain.Seguro;
import br.com.banksecure.app.dto.response.ApoliceResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-12T11:36:41-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class ApoliceMapperImpl implements ApoliceMapper {

    @Override
    public ApoliceResponse converterParaResponse(Apolice apolice) {
        if ( apolice == null ) {
            return null;
        }

        Long clienteId = null;
        Long seguroId = null;
        Long id = null;
        BigDecimal valorFinal = null;
        LocalDate inicioVigencia = null;
        LocalDate fimVigencia = null;

        clienteId = apoliceClienteId( apolice );
        seguroId = apoliceSeguroId( apolice );
        id = apolice.getId();
        valorFinal = apolice.getValorFinal();
        inicioVigencia = apolice.getInicioVigencia();
        fimVigencia = apolice.getFimVigencia();

        ApoliceResponse apoliceResponse = new ApoliceResponse( id, clienteId, seguroId, valorFinal, inicioVigencia, fimVigencia );

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
