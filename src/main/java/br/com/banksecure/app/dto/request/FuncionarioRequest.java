package br.com.banksecure.app.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FuncionarioRequest(
        @NotBlank(message = "Nome é obrigatório.")
        String nome,

        @NotBlank(message = "Cargo é obrigatório.")
        String cargo,

        @NotBlank(message = "Usuário é obrigatório.")
        String username,

        @NotBlank(message = "Senha é obrigatória.")
        String password) {}
