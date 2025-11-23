package br.com.banksecure.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SeguroRequest(
        @NotBlank(message = "Título é obrigatório.")
        String titulo,

        String coberturaMinima,

        @NotNull(message = "O prêmio base é obrigatório.")
        @Positive(message = "O valor do prêmio deve ser maior que zero.")
        BigDecimal valorPremioBase
) {
}
