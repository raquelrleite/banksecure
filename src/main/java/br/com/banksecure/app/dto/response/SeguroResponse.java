package br.com.banksecure.app.dto.response;

import java.math.BigDecimal;

public record SeguroResponse(Long id, String titulo, String coberturaMinima, BigDecimal valorPremioBase) {
}
