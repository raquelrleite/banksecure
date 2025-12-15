package br.com.banksecure.app.dto.response;

public record BemResponse(
        Long id,
        Long clienteId,
        String tipo,
        String descricao
) {
}
