package br.com.banksecure.app.builder;

import br.com.banksecure.app.domain.Seguro;
import br.com.banksecure.app.enums.TipoSeguroeBem;

import java.math.BigDecimal;

public class SeguroBuilder {
    private Seguro seguro;

    private SeguroBuilder() {
        seguro = new Seguro();
        seguro.setId(null);
        seguro.setTitulo("Seguro Residencial");
        seguro.setCoberturaMinima("Cobertura básica para imprevistos.");
        seguro.setValorPremioBase(new BigDecimal("150.00"));
        seguro.setTipo(TipoSeguroeBem.RESIDENCIAL);
    }

    public static SeguroBuilder umSeguro() {
        return new SeguroBuilder();
    }

    public SeguroBuilder withId(Long id) {
        seguro.setId(id);
        return this;
    }
    public SeguroBuilder withTitulo(String titulo) {
        seguro.setTitulo(titulo);
        return this;
    }
    public SeguroBuilder withCoberturaMinima(String coberturaMinima) {
        seguro.setCoberturaMinima(coberturaMinima);
        return this;
    }
    public SeguroBuilder withValorPremioBase(BigDecimal valorPremioBase) {
        seguro.setValorPremioBase(valorPremioBase);
        return this;
    }
    public SeguroBuilder withTipo(TipoSeguroeBem tipo) {
        seguro.setTipo(tipo);
        return this;
    }
    public Seguro build() {
        return seguro;
    }
}
