package br.com.banksecure.app.dto.response;

import br.com.banksecure.app.enums.TipoSeguroeBem;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SeguroResponse(
        Long id,
        String titulo,
        String coberturaMinima,
        BigDecimal valorPremioBase,
        TipoSeguroeBem tipo) {
}
