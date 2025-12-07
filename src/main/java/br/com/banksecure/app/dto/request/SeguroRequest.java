package br.com.banksecure.app.dto.request;

import br.com.banksecure.app.enums.TipoDeSeguro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SeguroRequest(
        @NotBlank(message = "O título do seguro é obrigatório.")
        String titulo,

        String coberturaMinima,

        @NotNull(message = "O valor do prêmio base é obrigatório.")
        @Positive(message = "O valor do prêmio base deve ser maior que zero.")
        BigDecimal valorPremioBase,

        @NotNull(message = "O tipo de seguro é obrigatório.")
        TipoDeSeguro tipo
) {
}
