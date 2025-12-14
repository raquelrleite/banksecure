package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.Funcionario;
import br.com.banksecure.app.dto.request.FuncionarioRequest;
import br.com.banksecure.app.dto.response.FuncionarioResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-14T02:07:14-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class FuncionarioMapperImpl implements FuncionarioMapper {

    @Override
    public Funcionario converterParaEntity(FuncionarioRequest request) {
        if ( request == null ) {
            return null;
        }

        Funcionario.FuncionarioBuilder funcionario = Funcionario.builder();

        funcionario.nome( request.nome() );
        funcionario.cargo( request.cargo() );
        funcionario.username( request.username() );
        funcionario.password( request.password() );

        return funcionario.build();
    }

    @Override
    public FuncionarioResponse converterParaResponse(Funcionario funcionario) {
        if ( funcionario == null ) {
            return null;
        }

        FuncionarioResponse.FuncionarioResponseBuilder funcionarioResponse = FuncionarioResponse.builder();

        return funcionarioResponse.build();
    }
}
