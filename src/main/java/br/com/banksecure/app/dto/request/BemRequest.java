package br.com.banksecure.app.dto.request;

import br.com.banksecure.app.enums.TipoSeguroeBem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BemRequest(
        @NotNull(message = "O ID do cliente proprietário do bem é obrigatório.")
        Long clienteId,

        @NotNull(message = "O tipo do bem (ex: Carro, Casa) é obrigatório.")
        TipoSeguroeBem tipo,

        String descricao) {
}
