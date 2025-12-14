package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.Cliente;
import br.com.banksecure.app.dto.request.ClienteRequest;
import br.com.banksecure.app.dto.response.ClienteResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-14T02:07:14-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public Cliente converterParaEntity(ClienteRequest request) {
        if ( request == null ) {
            return null;
        }

        Cliente.ClienteBuilder cliente = Cliente.builder();

        cliente.nome( request.nome() );
        cliente.cpf( request.cpf() );
        cliente.dataNascimento( request.dataNascimento() );

        return cliente.build();
    }

    @Override
    public ClienteResponse converterParaResponse(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        ClienteResponse.ClienteResponseBuilder clienteResponse = ClienteResponse.builder();

        return clienteResponse.build();
    }
}
