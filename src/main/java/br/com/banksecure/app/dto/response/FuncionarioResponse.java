package br.com.banksecure.app.dto.response;

import lombok.Builder;

@Builder
public record FuncionarioResponse(
        Long id,
        String nome,
        String cargo,
        String username) {
}
