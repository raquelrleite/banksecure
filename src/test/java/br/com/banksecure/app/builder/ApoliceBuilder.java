package br.com.banksecure.app.builder;

import br.com.banksecure.app.domain.Apolice;
import br.com.banksecure.app.domain.Bem;
import br.com.banksecure.app.domain.Cliente;
import br.com.banksecure.app.domain.Seguro;
import br.com.banksecure.app.enums.ApoliceStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ApoliceBuilder {

    private final Apolice apolice;

    private ApoliceBuilder() {
        apolice = new Apolice();
        apolice.setId(1L);

        Cliente clientePrincipal = ClienteBuilder.umCliente()
                .withId(1L)
                .build();

        Bem bemDoCliente = BemBuilder.umBem()
                .withId(1L)
                .withCliente(clientePrincipal)
                .build();

        Seguro seguro = SeguroBuilder.umSeguro().withId(1L).build();

        apolice.setCliente(clientePrincipal);
        apolice.setBem(bemDoCliente);
        apolice.setSeguro(seguro);
        apolice.setValorFinal(new BigDecimal("200.00"));
        apolice.setInicioVigencia(LocalDate.now());
        apolice.setFimVigencia(LocalDate.now().plusYears(1));
        apolice.setStatus(ApoliceStatus.ATIVA);
    }

    public static ApoliceBuilder umaApolice() {
        return new ApoliceBuilder();
    }

    public ApoliceBuilder withId(Long id) {
        apolice.setId(id);
        return this;
    }

    public ApoliceBuilder withCliente(Cliente cliente) {
        apolice.setCliente(cliente);
        return this;
    }

    public ApoliceBuilder withSeguro(Seguro seguro) {
        apolice.setSeguro(seguro);
        return this;
    }
    public ApoliceBuilder withBem(Bem bem) {
        apolice.setBem(bem);
        return this;
    }
    public ApoliceBuilder withValorFinal(BigDecimal valorFinal) {
        apolice.setValorFinal(valorFinal);
        return this;
    }
    public ApoliceBuilder withInicioVigencia(LocalDate inicioVigencia) {
        apolice.setInicioVigencia(inicioVigencia);
        return this;
    }
    public ApoliceBuilder withFimVigencia(LocalDate fimVigencia) {
        apolice.setFimVigencia(fimVigencia);
        return this;
    }
    public ApoliceBuilder withStatus(ApoliceStatus status) {
        apolice.setStatus(status);
        return this;
    }
    public Apolice build() {
        return apolice;
    }
}
