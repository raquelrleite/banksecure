package br.com.banksecure.app.builder;

import br.com.banksecure.app.domain.Bem;
import br.com.banksecure.app.domain.Cliente;
import br.com.banksecure.app.enums.TipoSeguroeBem;

import static br.com.banksecure.app.builder.ClienteBuilder.umCliente;

public class BemBuilder {

    private Bem bem;

    private BemBuilder() {
        bem = new Bem();
        bem.setId(1L);
        bem.setCliente(umCliente().build());
        bem.setTipo(TipoSeguroeBem.RESIDENCIAL);
        bem.setDescricao("Rua Localhost, nº 127.0.0.1, Apto 404");
    }

    public static BemBuilder umBem() {
        return new BemBuilder();
    }

    public BemBuilder withId(Long id) {
        bem.setId(id);
        return this;
    }

    public BemBuilder withCliente(Cliente cliente) {
        bem.setCliente(cliente);
        return this;
    }

    public BemBuilder withTipo(TipoSeguroeBem tipo) {
        bem.setTipo(tipo);
        return this;
    }

    public BemBuilder withDescricao(String descricao) {
        bem.setDescricao(descricao);
        return this;
    }

    public Bem build() {
        return bem;
    }
}
