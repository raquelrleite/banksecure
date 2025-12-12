package br.com.banksecure.app.dto.response;

public record BemResponse(
        Long id,
        String tipoBem,
        String descricao
) {
}
