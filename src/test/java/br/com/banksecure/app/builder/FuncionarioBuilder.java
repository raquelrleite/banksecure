package br.com.banksecure.app.builder;

import br.com.banksecure.app.domain.Funcionario;

public class FuncionarioBuilder {

    private Funcionario funcionario;

    private FuncionarioBuilder() {
        funcionario = new Funcionario();
        funcionario.setId(null);
        funcionario.setNome("Ana Silva");
        funcionario.setCargo("Especialista");
        funcionario.setUsername("ana.silva");
        funcionario.setPassword("senha123");
    }

    public static FuncionarioBuilder umFuncionario() {
        return new FuncionarioBuilder();
    }

    public FuncionarioBuilder withId(Long id) {
        funcionario.setId(id);
        return this;
    }
    public FuncionarioBuilder withNome(String nome) {
        funcionario.setNome(nome);
        return this;
    }
    public FuncionarioBuilder withCargo(String cargo) {
        funcionario.setCargo(cargo);
        return this;
    }
    public FuncionarioBuilder withUsername(String username) {
        funcionario.setUsername(username);
        return this;
    }
    public FuncionarioBuilder withPassword(String password) {
        funcionario.setPassword(password);
        return this;
    }
    public Funcionario build() {
        return funcionario;
    }
}
