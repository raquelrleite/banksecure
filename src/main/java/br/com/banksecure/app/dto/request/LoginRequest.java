package br.com.banksecure.app.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "Usuário obrigatório.")
        String username,

        @NotBlank(message = "Senha obrigatória.")
        String password) {
}
