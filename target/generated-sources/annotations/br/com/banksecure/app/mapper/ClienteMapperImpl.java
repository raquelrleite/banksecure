package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Cliente;
import br.com.banksecure.app.dto.request.ClienteRequest;
import br.com.banksecure.app.dto.response.ClienteResponse;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-08T21:02:01-0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public Cliente converterParaEntity(ClienteRequest request) {
        if ( request == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setCpf( request.cpf() );
        cliente.setDataNascimento( request.dataNascimento() );
        cliente.setNome( request.nome() );

        return cliente;
    }

    @Override
    public ClienteResponse converterParaResponse(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String cpf = null;
        LocalDate dataNascimento = null;

        id = cliente.getId();
        nome = cliente.getNome();
        cpf = cliente.getCpf();
        dataNascimento = cliente.getDataNascimento();

        ClienteResponse clienteResponse = new ClienteResponse( id, nome, cpf, dataNascimento );

        return clienteResponse;
    }
}
