package br.com.banksecure.app.dto.request;

import jakarta.validation.constraints.NotNull;

public record RenovacaoApoliceRequest(
        @NotNull(message = "O ID da apólice é obrigatório")
        Long idApolice
) { }