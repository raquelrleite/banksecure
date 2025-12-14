package br.com.banksecure.app.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SeguroUpdateRequest(
        String titulo,
        String coberturaMinima,
        BigDecimal valorPremioBase
) {
}
