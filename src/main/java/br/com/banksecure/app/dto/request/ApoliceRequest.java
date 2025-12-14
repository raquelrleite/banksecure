package br.com.banksecure.app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ApoliceRequest(
        @NotNull(message = "O ID do cliente é obrigatório")
        Long clienteId,

        @NotNull(message = "O ID do seguro é obrigatório")
        Long seguroId,

        Long bemId
) {
}
