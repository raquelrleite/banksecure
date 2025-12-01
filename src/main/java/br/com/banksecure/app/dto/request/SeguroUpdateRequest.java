package br.com.banksecure.app.dto.request;

import java.math.BigDecimal;

public record SeguroUpdateRequest(
        String titulo,
        String coberturaMinima,
        BigDecimal valorPremioBase
) {
}
