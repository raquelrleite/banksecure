package br.com.banksecure.app.mapper;

import br.com.banksecure.app.domain.entity.Funcionario;
import br.com.banksecure.app.dto.request.FuncionarioRequest;
import br.com.banksecure.app.dto.response.FuncionarioResponse;
import br.com.banksecure.app.dto.response.LoginResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-01T15:24:25-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class FuncionarioMapperImpl implements FuncionarioMapper {

    @Override
    public Funcionario converterParaEntity(FuncionarioRequest request) {
        if ( request == null ) {
            return null;
        }

        Funcionario funcionario = new Funcionario();

        funcionario.setNome( request.nome() );
        funcionario.setCargo( request.cargo() );
        funcionario.setUsername( request.username() );
        funcionario.setPassword( request.password() );

        return funcionario;
    }

    @Override
    public FuncionarioResponse converterParaResponse(Funcionario funcionario) {
        if ( funcionario == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String cargo = null;
        String username = null;

        id = funcionario.getId();
        nome = funcionario.getNome();
        cargo = funcionario.getCargo();
        username = funcionario.getUsername();

        FuncionarioResponse funcionarioResponse = new FuncionarioResponse( id, nome, cargo, username );

        return funcionarioResponse;
    }

    @Override
    public LoginResponse converterParaLogin(Funcionario funcionario) {
        if ( funcionario == null ) {
            return null;
        }

        Long id = null;

        id = funcionario.getId();

        LoginResponse loginResponse = new LoginResponse( id );

        return loginResponse;
    }
}
