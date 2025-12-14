package br.com.banksecure.app.dto.response;

import br.com.banksecure.app.enums.TipoSeguroeBem;
import lombok.Builder;

@Builder
public record BemResponse(
        Long id,
        TipoSeguroeBem tipo,
        String descricao
) {
}
