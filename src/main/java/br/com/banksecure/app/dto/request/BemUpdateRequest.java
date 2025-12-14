package br.com.banksecure.app.dto.request;

import br.com.banksecure.app.enums.TipoSeguroeBem;
import lombok.Builder;

@Builder
public record BemUpdateRequest(
        TipoSeguroeBem tipo,
        String descricao) {
}
