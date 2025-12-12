package br.com.banksecure.app.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "Por favor, informe o usuário.")
        String username,

        @NotBlank(message = "Por favor, informe a senha.")
        String password) {
}
