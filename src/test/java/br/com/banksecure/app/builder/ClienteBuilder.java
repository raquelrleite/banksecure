package br.com.banksecure.app.builder;

import br.com.banksecure.app.domain.Cliente;

import java.time.LocalDate;

public class ClienteBuilder {
    private Cliente cliente;

    private ClienteBuilder() {
        cliente = new Cliente();
        cliente.setId(null);
        cliente.setNome("Nicholas");
        cliente.setCpf("006.737.490-53");
        cliente.setDataNascimento(LocalDate.of(1960, 10, 10));
    }

    public static ClienteBuilder umCliente() {
        return new ClienteBuilder();
    }

    public ClienteBuilder withId(Long id) {
        cliente.setId(id);
        return this;
    }
    public ClienteBuilder withNome(String nome) {
        cliente.setNome(nome);
        return this;
    }
    public ClienteBuilder withCpf(String cpf) {
        cliente.setCpf(cpf);
        return this;
    }
    public ClienteBuilder withDataNascimento(LocalDate dataNascimento) {
        cliente.setDataNascimento(dataNascimento);
        return this;
    }
    public Cliente build() {
        return cliente;
    }
}