package br.com.banksecure.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BemRequest(
        @NotNull(message = "O ID do cliente proprietário do bem é obrigatório.")
        Long clienteId,

        @NotBlank(message = "O tipo do bem (ex: Carro, Casa) é obrigatório.")
        String tipo,

        String descricao) {
}
